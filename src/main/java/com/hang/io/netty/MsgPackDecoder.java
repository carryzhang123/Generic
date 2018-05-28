package com.hang.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @author ZhangHang
 * @create 2017-07-26 10:33
 **/
public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf> {
    /**
     * 1、decode为ChannelHandler业务处理中的ChannelInboundHandler
     * 2、将ByteBuf中的字节转换成消息体
     * 3、在接受的消息写入ByteBuf后使用
     */
    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> list) throws Exception {
        //创建新的字节数组
        int length=byteBuf.readableBytes();
        byte[] array = new byte[length];
        //将ByteBuf的数据解码，放入message消息里
        byteBuf.getBytes(byteBuf.readerIndex(), array, 0, byteBuf.readableBytes());
        MessagePack msgpack = new MessagePack();
        list.add(msgpack.read(array));
    }
}
