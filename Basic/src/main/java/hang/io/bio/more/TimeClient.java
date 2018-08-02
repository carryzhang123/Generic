package hang.io.bio.more;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author ZhangHang
 * @create 2017-07-20 12:15
 * 同步阻塞I/O客户端
 **/
public class TimeClient {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", port);

            out = new PrintWriter(socket.getOutputStream(), true);
            for(int i=0;i<5;i++){
                out.println("Hello Server!");
                Thread.sleep(1000);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String resp = in.readLine();
                System.out.println("Get From Server：" + resp);
            }
            while (true){

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
                out = null;
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }

        }
    }
}
