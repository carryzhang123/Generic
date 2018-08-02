package hang.io.nio;

import com.hang.tools.threadpool.ExecutorUtils;

/**
 * @author ZhangHang
 * @create 2018-05-26 17:18
 **/
public class Test {
    public static void main(String[] args) throws Exception {
        ExecutorUtils.addTask(new Runnable() {
            @Override
            public void run() {
                Server server=new Server();
                try {
                    server.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ExecutorUtils.addTask(new Runnable() {
            @Override
            public void run() {
                Client client=new Client();
                try {
                    client.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
