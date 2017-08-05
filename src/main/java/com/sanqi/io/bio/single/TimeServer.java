package com.sanqi.io.bio.single;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ZhangHang
 * @create 2017-07-20 11:42
 * 同步阻塞I/O服务端
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
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(port);
            System.out.println("The time server is start in port："+port);
            Socket socket=null;
            while (true){
                socket=serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        }finally {
            if(serverSocket!=null){
                System.out.println("The time server close");
                serverSocket.close();
                serverSocket=null;
            }
        }
    }
}
