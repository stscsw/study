package httpServer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;

    /**
     * 开启服务器
     */
    public void start() {
        try {
            // 新建服务器后等待客户端连接
            // 对应访问地址为：http://localhost:8888/
            server = new ServerSocket(8888);
            acceptClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接客户端
     */
    private void acceptClient() {
        while(true){
            // 等待客户端连接
            try (Socket client = server.accept()){
                // 选择字节输入流进行读取
                BufferedInputStream bis = new BufferedInputStream(
                        client.getInputStream()
                );


                // 获取输入流字节的长度并创建对应的字节数组
                StringBuilder sb = new StringBuilder();
                byte[] buffer = new byte[1000];
                int len = 0;
                while ((len = bis.read(buffer))!=-1){
                    String tmp = new String(buffer, 0, len);
                    System.out.println(tmp);
                    sb.append(tmp);
                }
                // 去除请求中的空格，方便后续使用
                String request = sb.toString().trim();
                System.out.println(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭服务器
     */
    public void stop(){
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
