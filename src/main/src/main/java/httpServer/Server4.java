package httpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server4 {


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("启动服务");
        Socket socket = null;
        while (!serverSocket.isClosed()) {
            try {
                socket = serverSocket.accept();
                System.out.println("接受到客户端请求");
                InputStreamReader is = new InputStreamReader(socket.getInputStream());
                char[] bs = new char[2048];
                StringBuilder msg = new StringBuilder();
                // 如果10毫秒还没有数据，则视同没有新的数据了。
                // 因为有Keep-Alive的缘故，浏览器可能不主动断开连接的。
                // 实际应用，会根据协议第一行是GET还是 POST确定。
                socket.setSoTimeout(10);
                int len;
                try {
                    while ((len = is.read(bs)) != -1) {
                        msg.append(bs, 0, len);
                        msg.append("\n");
                    }
                } catch (Exception ex) {
                    // ex.printStackTrace();
                }
                System.out.println(msg);
                OutputStream outputStream = socket.getOutputStream();
                PrintStream out = new PrintStream(outputStream);
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type:text/html;charset=UTF-8");
                out.println();
                // 2、输出主页信息
                out .println("<HTML><BODY>"
                                + "<center>"
                                + "<H1>HTTP协议测试服务器,当前时间："
                                + new java.util.Date()
                                + "</h1>"
                                + "<form method='get'>username:<input type='text' name='username'/>password:<input type='text' name='password'/><input type='submit' value='GET测试'/></form><br/>"
                                + "<form method='post'>username:<input type='text' name='username'/>password:<input type='text' name='password'/><input type='submit' value='POST测试'/></form><br/>"
                                + "<form method='post'  enctype='multipart/form-data'>phototitle:<input type='text' name='phototitle'/>photo:<input type='file' name='photo'/><input type='submit' value='Upload测试'/></form>"
                                + "</center>您提交的数据如下:<pre>" + msg.toString() + "</pre></BODY></HTML>");

                is.close();
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    socket.close();
                }
            }
        }


    }

}
