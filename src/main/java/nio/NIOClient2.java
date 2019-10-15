package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Set;

public class NIOClient2 {

    // 服务端地址
    private InetSocketAddress SERVER;

    // 用于接收数据的缓冲区
    private ByteBuffer rBuffer = ByteBuffer.allocate(1024);

    // 用于发送数据的缓冲区
    private ByteBuffer sBuffer = ByteBuffer.allocate(1024);

    // 用于监听通道事件
    private static Selector selector;

    // 用于编/解码 buffer
    private Charset charset = Charset.forName("UTF-8");

    public NIOClient2(int port) {
        SERVER = new InetSocketAddress("localhost", port);
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 初始化客户端
    private void init() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(SERVER);
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> handle(selectionKey));
            selectionKeys.clear(); // 清除处理过的事件
        }
    }

    /**
     * 处理事件
     */
    private void handle(SelectionKey selectionKey) {
        try {
            // 连接就绪事件
            if (selectionKey.isConnectable()) {
                SocketChannel client = (SocketChannel) selectionKey.channel();
                client.configureBlocking(false);
                if (client.isConnectionPending()) {
                    client.finishConnect();
                    System.out.println("连接成功！");
                    // 启动线程监听客户端输入
                    new Thread() {
                        @Override
                        public void run() {
                            while (true) {
                                try {
                                    sBuffer.clear();
                                    Scanner scanner = new Scanner(System.in);
                                    String sendText = scanner.nextLine();
                                    System.out.println(sendText);
                                    sBuffer.put(charset.encode(sendText));
                                    sBuffer.flip();
                                    client.write(sBuffer);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }.start();
                }
                // 注册可读事件
                client.register(selector, SelectionKey.OP_READ);
            }
            // 可读事件，有从服务器端发送过来的信息，读取输出到屏幕上
            else if (selectionKey.isReadable()) {
                SocketChannel client = (SocketChannel) selectionKey.channel();
                rBuffer.clear();
                int count = client.read(rBuffer);
                if (count > 0) {
                    String receiveText = new String(rBuffer.array(), 0, count);
                    System.out.println(receiveText);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new NIOClient2(7777);
    }
}