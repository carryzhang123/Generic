package com.hang.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.Date;

/**
 * @author ZhangHang
 * @create 2017-07-24 10:27
 **/
public class  EchoServerHandler extends ChannelHandlerAdapter {

    /**
     * 1、数据输出到ByteBuf中并经过ChannelInboundHandler的decoder生成message，读取msg
     * 2、自定义要输出的结果，并经过ChannelOutboundHandler的encoder存储到ByteBuf中
     * 3、发送ByteBuf到SockerChannel中
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server receive：" + msg);

        ctx.write(msg);
        System.out.println("server send:"+msg);
    }

    /**
     * 将ChannelHanlerContext写入Pipline中返回
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 对出现异常时的处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

