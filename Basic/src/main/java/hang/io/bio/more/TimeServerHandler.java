package hang.io.bio.more;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author ZhangHang
 * @create 2017-07-20 11:49
 **/
public class TimeServerHandler implements Runnable {
    private Socket socket;
    private static int i = 1;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String body;
            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("Get From Client：" + body + "   第" + i++ + "个"+Thread.currentThread().getName());
                out.println("Hello Client!");
            }
        } catch (Exception e) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                this.socket = null;
            }
        }
    }
}
