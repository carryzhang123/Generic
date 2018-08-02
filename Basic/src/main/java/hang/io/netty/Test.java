package hang.io.netty;

import com.hang.tools.threadpool.ExecutorUtils;

/**
 * @author ZhangHang
 * @create 2018-05-28 17:50
 **/
public class Test {
    public static void main(String[] args) {

        ExecutorUtils.addTask(new Runnable() {
            @Override
            public void run() {
                EchoServer server=new EchoServer();
                try {
                    server.bind(5002);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ExecutorUtils.addTask(new Runnable() {
            @Override
            public void run() {
                EchoClient echoClient=new EchoClient("127.0.0.1",5002);
                try {
                    echoClient.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
