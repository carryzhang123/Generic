package hang.io.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author ZhangHang
 * @create 2017-07-26 10:48
 **/
public class EchoClientHandler extends ChannelHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String message="userName=zhanghang    password=123456   !";

        ctx.writeAndFlush(message);

        System.out.println("client send:"+message);

        ctx.writeAndFlush(message);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client receive: " + msg);

        ctx.write(msg);

        System.out.println("client send:"+msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
