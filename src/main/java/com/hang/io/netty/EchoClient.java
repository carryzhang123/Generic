package com.hang.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author ZhangHang
 * @create 2017-07-26 10:41
 **/
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {

        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.TCP_NODELAY,true);
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast(new MsgPackDecoder(),new MsgpackEncoder(),new EchoClientHandler());
                }
            });
//            b.group(loopGroup).channel(NioSocketChannel.class)
//                    .option(ChannelOption.TCP_NODELAY, true)
//                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
//                    .handler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
//                            ch.pipeline().addLast("msgpack decoder", new MsgPackDecoder());
//                            ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
//                            ch.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
//                            ch.pipeline().addLast(new EchoClientHandler(sendNumber));
//                        }
//                    });
            //发起异步连接操作
            ChannelFuture f = bootstrap.connect(host, port).sync();
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        } finally {
            //优雅退出，释放NIO线程组
            loopGroup.shutdownGracefully();
        }
    }
}
