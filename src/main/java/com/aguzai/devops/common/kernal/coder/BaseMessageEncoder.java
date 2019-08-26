package com.aguzai.devops.common.kernal.coder;

import com.aguzai.devops.common.kernal.message.BaseMessage;
import com.aguzai.devops.common.kernal.message.BaseRequest;
import com.aguzai.devops.common.kernal.message.ByteBufTools;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.Random;

/**
 * Created by zhengchen on 16/11/14.
 */
public class BaseMessageEncoder extends MessageToByteEncoder<BaseMessage> {

    private Random random = new Random();
    //private Kryo kryo = new Kryo();

    @Override
    protected void encode(ChannelHandlerContext ctx, BaseMessage msg, ByteBuf out) throws Exception {
        ByteBuf subBuf = Unpooled.buffer(1024);
        msg.toBytes(subBuf);
        ByteBufTools.writeBytes(out,"!ARQ".getBytes(),0,4);
        if(msg instanceof BaseRequest){
            ByteBufTools.writeShort(out, ((BaseRequest)msg).getRequestSeq());
        }else {
            Short requestSeq = (short) random.nextInt();
            ByteBufTools.writeShort(out, requestSeq);
        }
        ByteBufTools.writeShort(out,msg.getMessageId());
        ByteBufTools.writeShort(out,msg.getVersion());
        ByteBufTools.writeInteger(out,subBuf.writerIndex());
        ByteBufTools.writeBytes(out,subBuf.array(),0,subBuf.writerIndex());
    }
}
