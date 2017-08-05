package com.sanqi.io.netty.coding.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @author ZhangHang
 * @create 2017-07-26 10:33
 * 得到ByteBuf数据流，然后进行解码
 **/
public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext arg0, ByteBuf arg1, List<Object> arg2) throws Exception {
        final byte[] array;
        final int length=arg1.readableBytes();
        array=new byte[length];
        arg1.getBytes(arg1.readerIndex(),array,0,length);
        MessagePack msgpack=new MessagePack();
        arg2.add(msgpack.read(array));
    }
}
