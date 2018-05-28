package com.hang.io.netty;

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
                    server.bind(8000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ExecutorUtils.addTask(new Runnable() {
            @Override
            public void run() {
                EchoClient echoClient=new EchoClient("127.0.0.1",8080);
                try {
                    echoClient.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
