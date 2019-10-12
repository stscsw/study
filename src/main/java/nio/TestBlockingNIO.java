package nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

//NIO 阻塞方式
public class TestBlockingNIO {

    //客户端
    @Test
    public void clint() throws IOException {
        //获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        FileChannel fileChannel = FileChannel.open(Paths.get("D:/1.jpg"), StandardOpenOption.READ);

        //分配指定大小的缓冲区
        ByteBuffer bf = ByteBuffer.allocate(1024);

        //获取本地文件 并发送到服务器
        while (fileChannel.read(bf) != -1) {
            bf.flip();
            sChannel.write(bf);
            bf.clear();
        }

        //通知服务器端 客户端已经输入完毕
        sChannel.shutdownOutput();

        int length = 0;
        while ((length = sChannel.read(bf)) != -1) {
            bf.flip();
            System.out.println(new String(bf.array(), 0, length));
            bf.clear();
        }

        //关闭通道
        fileChannel.close();
        sChannel.close();
    }

    //服务端
    @Test
    public void server() throws IOException {
        //获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        FileChannel fileChannel = FileChannel.open(Paths.get("D:/2.jpg"), StandardOpenOption.READ,
                StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //绑定端口
        ssChannel.bind(new InetSocketAddress(8888));

        //获取客户端连接通道
        SocketChannel sChannel = ssChannel.accept();

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (sChannel.read(buffer) != -1) {
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
        }

        buffer.put("服务端接收完毕".getBytes());
        buffer.flip();
        sChannel.write(buffer);

        //关闭通道
        sChannel.close();
        fileChannel.close();
        ssChannel.close();

    }

}
