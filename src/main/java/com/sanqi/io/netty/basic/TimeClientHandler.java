package com.sanqi.io.netty.basic;

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
    private static final Logger LOGGER= Logger.getLogger(TimeClientHandler.class.getName());
    private final ByteBuf firstMessage;
     public TimeClientHandler(){
         byte[] req="QUERY TIME ORDER".getBytes();
         firstMessage= Unpooled.buffer(req.length);
         firstMessage.writeBytes(req);
     }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
         //请求消息发送给服务端
        ctx.writeAndFlush(firstMessage);
    }

    /**
     * 服务端返回应答消息时，调用此方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf= (ByteBuf) msg;
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req,"UTF-8");
        System.out.println("Now is："+body);
    }

    /**
     * 当发生异常，调用此方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //释放资源
        LOGGER.warning("Unexpected exception from downstream : "+cause.getMessage());
        ctx.close();
    }
}
