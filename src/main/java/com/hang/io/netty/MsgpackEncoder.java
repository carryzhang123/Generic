package com.hang.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @author ZhangHang
 * @create 2017-07-26 10:29
 **/
public class MsgpackEncoder extends MessageToByteEncoder<Object> {
    /**
     * 1、encode为ChannelHandler业务处理中的ChannelOutboundHandler
     * 2、将消息体转成byte字节码，并写入ByteBuf中
     * 3、在消息体向外发送时使用
     */
    @Override
    protected void encode(ChannelHandlerContext context, Object tmpBytes, ByteBuf byteBuf) throws Exception {
        //将message消息写入新的数组中
        MessagePack msgpack = new MessagePack();
        byte[] newBytes = msgpack.write(tmpBytes);
        System.out.println("encode length:"+newBytes.length);

        //刷新到bytebuf里进行传输
        byteBuf.writeBytes(newBytes);
    }
}
