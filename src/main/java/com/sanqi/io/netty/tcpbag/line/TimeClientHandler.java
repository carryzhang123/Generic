package com.sanqi.io.netty.tcpbag.line;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

/**
 * @author ZhangHang
 * @create 2017-07-24 11:31
 **/
public class TimeClientHandler extends ChannelHandlerAdapter {
    private static final Logger LOGGER = Logger.getLogger(TimeClientHandler.class.getName());
    private int counter;
    private byte[] req;

    public TimeClientHandler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //请求消息发送给服务端
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    /**
     * 服务端返回应答消息时，调用此方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body= (String) msg;
        System.out.println("Now is：" + body + " ; the counter is : " + ++counter);
    }

    /**
     * 当发生异常，调用此方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //释放资源
        LOGGER.warning("Unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }
}
