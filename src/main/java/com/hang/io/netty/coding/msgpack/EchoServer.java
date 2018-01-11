package com.hang.io.netty.coding.msgpack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author ZhangHang
 * @create 2017-07-24 10:12
 * LineBasedFrameDecoder：以换行符为分割界限  StringDecoder：将字节码自动转为String类型
 **/
public class EchoServer {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        bind(port);
    }

    public static void bind(int port) throws Exception {
        //配置服务度的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();//服务端接受客户端的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();//用于SocketChannel的网络读写
        try {
            //Netty用于启动NIO服务端的辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            //NioServerSocketChannel对于NIO的ServerSocketChannel类，ChildChannelHandler类似于Reactor模式的Handler类
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                            sc.pipeline().addLast("msgpack decoder", new MsgPackDecoder());
                            sc.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                            sc.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
                            sc.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            //绑定端口，同步等待成功，类似于JDK的Future，主要用于异步操作的通知回调
            ChannelFuture f = b.bind(port).sync();
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            //优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
