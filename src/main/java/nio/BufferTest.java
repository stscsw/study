package nio;

import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BufferTest {

    @Test
    public void tt() throws UnsupportedEncodingException {
        System.out.println("中文".getBytes().length);
        System.out.println("😊".getBytes().length);
        System.out.println("abcde".getBytes().length);
        System.out.println("中文".getBytes("GBK").length);
        System.out.println("😊".getBytes("GBK").length);
        System.out.println("abcde".getBytes("GBK").length);
        System.out.println(new String("😊".getBytes("GBK"), "GBK"));
    }


    //mark <= position <= limit <= capacity
    //ByteBuffer CharBuffer ShortBuffer IntBuffer LongBuffer FloatBuffer DoubleBuffer
    public static void main(String[] args) {
        //put()
        //get()
        //flip() 将limit设置为position的值 将position设置为0
        //rewind() 将position设置为0
        //mark()  将mark设置为position的值
        //reset() 将position设置为mark
        //clear()  清空缓冲区

        String str = "abcde";
        //1.分配一个指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("==================allocate================");
        System.out.println(buffer.position());//0
        System.out.println(buffer.limit());//1024
        System.out.println(buffer.capacity());//1024

        //2.利用put()把数据存入缓冲区
        buffer.put(str.getBytes());
        System.out.println("===================put====================");
        System.out.println(buffer.position());//5
        System.out.println(buffer.limit());//1024
        System.out.println(buffer.capacity());//1024

        //3.切换到读数据模式
        buffer.flip();
        System.out.println("===================flip====================");
        System.out.println(buffer.position());//0
        System.out.println(buffer.limit());//5
        System.out.println(buffer.capacity());//1024

        //4.读取缓冲区的数据
        byte[] dst = new byte[buffer.limit()];
        buffer.get(dst);
        System.out.println(new String(dst, 0, dst.length));//abcde
        System.out.println("===================get====================");
        System.out.println(buffer.position());//5
        System.out.println(buffer.limit());//5
        System.out.println(buffer.capacity());//1024

        //5.可重复读
        buffer.rewind();
        System.out.println("===================rewind====================");
        System.out.println(buffer.position());//0
        System.out.println(buffer.limit());//5
        System.out.println(buffer.capacity());//1024

        //6.清空缓冲区(但是缓冲区中的数据依然存在，只不过里面的数据处于被遗忘状态)
        buffer.clear();
        System.out.println("===================clear====================");
        System.out.println(buffer.position());//0
        System.out.println(buffer.limit());//1024
        System.out.println(buffer.capacity());//1024
    }

    /**
     * 用户地址空间  内核地址空间
     *
     * 非直接缓冲区：通过allocate()方法分配缓冲区，将缓冲区建立在JVM内存中
     * 直接缓冲区：通过allocateDirect()方法分配直接缓冲区，将缓冲区建立在物理内存中 （物理内存映射文件）
     */
    @Test
    public void isDirect() {
        //分配直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        System.out.println(buffer.isDirect());//判断是否是直接缓冲区
    }



    /**
     * 通道实现类
     * FileChannel
     * SocketChannel
     * ServerSocketChannel
     * DatagramChannel
     *
     * 获取通道方法
     * 1.流通过getChannel()方法
     * 2.通道类通过静态方法open()
     * 3.使用Files工具类的newByteChannel()方法获取
     *
     * 通道之间的数据传输：
     * transferTo()、transferForm()：将数据从源通道传输到其他 Channel
     *
     * 通道与缓冲区之间的数据传输
     * channel.write(buffer)：将缓冲区数据写入通道。
     * channel.read(buffer)：将通道中的数据读到缓冲区。
     */


    /**
     * 非直接缓冲区
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        FileInputStream fis = new FileInputStream("D://1.jpg");//读取项目中的1.jpg图片
        FileOutputStream fos = new FileOutputStream("D://2.jpg");//复制1.jpg，重命名为2.jpg
        //1.获取通道
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();
        //2.分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //3.将通道中的数据存入缓冲区
        while (inChannel.read(buffer) != -1) {
            buffer.flip();//切换成读数据模式
            //4.将缓冲区中的数据写入通道中
            outChannel.write(buffer);
            buffer.clear();
        }
        outChannel.close();
        inChannel.close();
        fos.close();
        fis.close();
    }

    /**
     * 直接缓存区物理内存地址（内存映射文件）
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("D://1.jpg"),
                StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("D://3.jpg"),
                StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        MappedByteBuffer inMap = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMap = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
        byte[] dst = new byte[inMap.limit()];
        inMap.get(dst);
        outMap.put(dst);

        inChannel.close();
        outChannel.close();
    }

    //通道之间的数据传输
    @Test
    public void test3() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("D://1.jpg"),
                StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("D://3.jpg"),
                StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
//        inChannel.transferTo(0,inChannel.size(),outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());
        inChannel.close();
        outChannel.close();
    }

    //分散读取与聚集写入
    @Test
    public void test4() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("D://1.txt", "rw");
        //1.获取通道
        FileChannel channel = raf.getChannel();
        //2.分配缓冲区
        ByteBuffer buffer1 = ByteBuffer.allocate(128);//缓冲区1
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);//缓冲区2
        //3.分散读取
        ByteBuffer[] bufs = {buffer1, buffer2};//将两个缓冲区加到数组中
        channel.read(bufs);//读取缓冲区数据
        for (ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();//修改成读模式
        }
        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("============================");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        //4.聚集写入
        RandomAccessFile raf1 = new RandomAccessFile("D://2.txt", "rw");
        FileChannel channel1 = raf1.getChannel();
        channel1.write(bufs);
    }

    //字符集(GBK编GBK解，不会乱码，GBK编UTF8解就会乱码)
    @Test
    public void test5() throws IOException {

        Charset.availableCharsets().entrySet().forEach(item -> {
            System.out.println(item.getKey()+item.getValue());
        });


        Charset cs1 = Charset.forName("GBK");//指定编码
        CharsetEncoder ce = cs1.newEncoder();//获取编码器
        CharsetDecoder cd = cs1.newDecoder();//获取解码器

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("花自飘零水自流");
        cBuf.flip();
        //编码
        ByteBuffer bBuf = ce.encode(cBuf);
        for (int i = 0; i < 14; i++) {
            System.out.println(bBuf.get());
        }
        System.out.println("===========================");
        //解码
        bBuf.flip();//重置limit和position

        byte[] dst = new byte[bBuf.limit()];
        bBuf.get(dst);
        System.out.println("我的"+new String(dst,cs1));

        bBuf.rewind();
        CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());
    }

}
