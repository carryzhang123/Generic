package com.hang.io.bio.more;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ZhangHang
 * @create 2017-07-20 14:39
 **/
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port=8080;
        if(args!=null&&args.length>0){
            try {
                port=Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        ServerSocket server=null;
        try {
            server=new ServerSocket(port);
            System.out.println("The time server is start in port："+port);
            Socket socket=null;
            TimeServerHanlerExecutePool singleExecutor=new TimeServerHanlerExecutePool(100,100);
            while (true){
                socket=server.accept();
                System.out.println(socket.getClass().getName());
                singleExecutor.execute(new TimeServerHandler(socket));
            }
        }finally {
            if(server!=null){
                System.out.println("The time server close");
                server.close();
                server=null;
            }
        }
    }
}
