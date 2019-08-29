package com.aguzai.devops.common.kernal.coder;


import com.aguzai.devops.common.kernal.Global;
import com.aguzai.devops.common.kernal.message.BaseMessage;
import com.aguzai.devops.common.kernal.message.BaseResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by zhengchen on 2017/1/17.
 */
public class BaseMessageDecoder extends ByteToMessageDecoder {
    //ThreadLocal<BigDataBuffer>
    static ThreadLocal<BigDataBuffer> manager= new ThreadLocal<BigDataBuffer>();

    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        BigDataBuffer bigDataBuf = manager.get();
        ByteBuf dataBuf;
        short requestSeq ;
        short messageId ;
        short version ;
        int length ;
        if(bigDataBuf != null){
            int dataLength = byteBuf.writerIndex();
            if(!bigDataBuf.appendData(byteBuf)){
                return;
            }
            requestSeq = bigDataBuf.getRequestSeq();
            messageId = bigDataBuf.getMessageId();
            version = bigDataBuf.getVersion();
            length = bigDataBuf.getLength();
            dataBuf = bigDataBuf.getBigDataBuf();
        } else {
            ByteBuf header = Unpooled.buffer(4);
            byteBuf.readBytes(header,4);
            byte[] headerBytes = header.array();
            if(headerBytes[0]!= 33 || headerBytes[1]!=65 || headerBytes[2]!=82 || headerBytes[3]!= 80)
                return;

             requestSeq = byteBuf.readShort();
             messageId = byteBuf.readShort();
             version = byteBuf.readShort();
             length = byteBuf.readInt();
             //byte[] data = byteBuf.array();
            if(length >byteBuf.writerIndex()-14){
                BigDataBuffer cache = new BigDataBuffer(requestSeq,messageId,version,length);
                cache.appendData(byteBuf);
                manager.set(cache);
                return;
            }
            dataBuf = byteBuf;
        }
        Class messageClass = Global.getMessageClass(messageId,version,2);

        if(messageClass != null) {
            BaseMessage message = (BaseMessage) messageClass.newInstance();
            if(message instanceof BaseResponse){
                ((BaseResponse)message).setRequestSeq(requestSeq);
            }
            message.fromBytes(dataBuf, messageId, version);
            list.add(message);
        } else {
            System.out.println("Can not parse Message id["+messageId+"] version["+version+"]");
        }
        manager.remove();
    }
}
