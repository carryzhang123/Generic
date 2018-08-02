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

/**
 * UDP：
 * 1、不需要ACK进行确认；
 * 2、有消息保护边界，即不用担心粘包问题，应用层可以明确知道哪些包属于相应的数据源；
 * TCP：
 * 1、TCP请求需要ACK进行返回；
 * 2、TCP是面向流的，没有消息保护边界，即无法区分一段连接的包，属于哪些数据源；
 * Delay ACK：
 * 1、如果服务端有响应消息发送，则ACK会和响应消息一起发送过去；
 * 2、如果客服端再次发送消息过来，则ACK也会立即返回；
 * 3、如果客服端没有再次发送消息且服务端暂时也没有消息要返回，则ACK会在一定时间外（40ms-200ms）返回；
 * Nagle算法：
 * 1、若小分组为MSS（网络传输数据最大值）大小，则无需等待ACK直接发送
 * 2、上个小分组的ACK返回后，客服端才能再次发送小分组；
 * 3、当上个小分组的ACK没有返回，且当前客服端不断有小分组要发送，则这些小分组会存储在缓存里汇聚成一个大的小分组，然后一次性发送；
 * 粘包：
 * 1、TCP发送端采用Nagle算法，由于TCP没有消息保护边界，则会产生粘包；
 * 2、TCP接收端会先把接受的包放入缓存中，由于TCP是面向流没有消息保护边界，则当缓存中有多个包时，应用层会把这些当作流，看成一个包做处理；
 * 拆包：
 * 1、关闭Nagle算法；
 * 2、采用定长计算，即规定每个数据的长度是多少，然后应用层会把固定长度的包最为一个数据源；
 * 3、格式化数据，在数据中加入开始和结束占位符，缺点是数据大的时候，可能会有数据和占位符重复；
 * 4、自定义头和内容，在数据头定义4个字节大小，记录这个数据的字节大小即包的长度和其他基础数据，然后应用层根据头中的大小去读取响应大小的包作为一个数据源；
 */
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
            //关闭Nagle算法，防止发送端粘包
            bootstrap.option(ChannelOption.TCP_NODELAY,true);
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast(new MsgPackDecoder(),new MsgpackEncoder(),new EchoClientHandler());
                }
            });
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
