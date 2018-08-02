package hang.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * @author ZhangHang
 * @create 2017-07-24 10:12
 **/

/**
 * 1、TCP有三次握手和四次分开，Socket是实现TCP协议的一种方式，实现后会一直连接，除非一端主动关闭，根据关闭的时间分为
 * TCP短连接即建立后立即关闭和TCP长连接建立后等一方主动关闭才会关闭
 * 2、http是无状态、无连接的，每次请求都是一个新的连接，http短连接采用的是tcp短连接，一个请求结束后立即关闭tcp连接，
 * http长连接采用tcp长连接，一个请求后不会关闭tcp连接，后面的新的请求都会采用这个tcp连接进行传输
 * 3、ByteBuf相对于ByteBuffer有三点优势：自动扩增、有read和write索引，read索引小于write索引、
 * 零复制，即可以把流中的字节分别用HeapByteBuf和DirectByteBuf存储；
 * 4、ChannelPipline是netty对Channel后续处理逻辑的一种封装，其中ChannelHandler是业务处理逻辑，ChannelContext是上下文，即当前的环境
 * 5、ChannelHandler，业务逻辑处理器，分为ChannelInboundHandler和ChannelOutboundHandler
 * 6、ChannelInboundHandler，输入数据处理器是从SocketChannel中的流存入到ByteBuf后，将ByteBuf中的数据经过自己的decoder转成消息对象
 * 7、ChannelOutboundHandler，输出业务处理器是将消息体经过encoder转成字节，存入到ByteBuf里，在有ByteBuf传入SocketChannel中进行传输
 * 8、Encoder这个类的实现类主要是将消息对象转成字节，存入ByteBuf对象中；
 * 9、Decoder这个类的实现类主要讲ByteBuf对象中的字节转成消息对象，其中ReplayingDecoder这个实现类只要ByteBuf中有数据就会自动去读取，不需提前做判断
 */
public class EchoServer {
    public void bind(int port) throws Exception {
        //服务端接受客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //用于SocketChannel的网络读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //Netty用于启动NIO服务端的辅助启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            //绑定处理group
            bootstrap.group(bossGroup, workerGroup);
            //指定待处理的Channel
            bootstrap.channel(NioServerSocketChannel.class);
            //group可以处理的最大连接数
            bootstrap.option(ChannelOption.SO_BACKLOG, 100);
            // 有数据立即发送，即禁止Nagle算法
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            // 保持连接
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            //处理新连接
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    //获取channel中的pipe管道，并对管道中的数据进行处理
                    ChannelPipeline pipeline = channel.pipeline();
                    //将管道中的数据加入handler类中进行处理，FixedLengthFrameDecoder第一个处理ByteBuf中的数据，定长55，超出截取，不够则等待
                    pipeline.addLast(new FixedLengthFrameDecoder(44), new MsgPackDecoder(), new MsgpackEncoder(), new EchoServerHandler());
                }
            });

            //绑定端口，同步等待成功
            ChannelFuture f = bootstrap.bind(port).sync();
            if (f.isSuccess()) {
                System.out.println("long connection started success");
            } else {
                System.out.println("long connection started fail");
            }

            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            //优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
