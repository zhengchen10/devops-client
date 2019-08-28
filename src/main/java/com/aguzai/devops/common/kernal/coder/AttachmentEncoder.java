package com.aguzai.devops.common.kernal.coder;

import com.aguzai.devops.common.kernal.message.BaseRequest;
import com.aguzai.devops.common.kernal.message.ByteBufTools;
import com.aguzai.devops.common.kernal.message.MessageWithAttachment;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.Random;

public class AttachmentEncoder extends MessageToByteEncoder<MessageWithAttachment> {

  private Random random = new Random();

  protected void encode(ChannelHandlerContext ctx, MessageWithAttachment msg, ByteBuf out) throws Exception {
    ByteBuf msgBuf = Unpooled.buffer(1024);
    ByteBuf subBuf = Unpooled.buffer(1024);

    msg.getMessage().toBytes(subBuf);
    ByteBufTools.writeBytes(msgBuf, "!ARQ".getBytes(), 0, 4);
    if (msg.getMessage() instanceof BaseRequest) {
      ByteBufTools.writeShort(msgBuf, ((BaseRequest) msg.getMessage()).getRequestSeq());
    } else {
      Short requestSeq = (short) random.nextInt();
      ByteBufTools.writeShort(msgBuf, requestSeq);
    }
    ByteBufTools.writeShort(msgBuf, msg.getMessage().getMessageId());
    ByteBufTools.writeShort(msgBuf, msg.getMessage().getVersion());
    ByteBufTools.writeInteger(msgBuf, subBuf.writerIndex());
    ByteBufTools.writeBytes(msgBuf, subBuf.array(), 0, subBuf.writerIndex());
    File attach = new File(msg.getAttachment());
    ByteBufTools.writeLong(msgBuf, attach.length());
    ctx.write(msgBuf);



    MessageDigest MD5 = MessageDigest.getInstance("MD5");
    FileInputStream fileInputStream = new FileInputStream(attach);
    byte[] buffer = new byte[4096];
    int length;
    while ((length = fileInputStream.read(buffer)) != -1) {
      MD5.update(buffer, 0, length);
      if(length == 4096)
        ctx.write(buffer);
      else {
        byte[] buffer1 = new byte[length];
        System.arraycopy(buffer,0,buffer1,0,length);
        ctx.write(buffer1);
      }
    }
    String md5 = new String(Hex.encodeHex(MD5.digest()));
    out.writeInt(md5.length());
    out.writeBytes(md5.getBytes());
  }

  /*@Override
  protected void encode(ChannelHandlerContext ctx, MessageWithAttachment msg, ByteBuf out) throws Exception {

    ByteBuf subBuf = Unpooled.buffer(1024);
    msg.getMessage().toBytes(subBuf);
    ByteBufTools.writeBytes(out, "!ARQ".getBytes(), 0, 4);
    if (msg.getMessage() instanceof BaseRequest) {
      ByteBufTools.writeShort(out, ((BaseRequest) msg.getMessage()).getRequestSeq());
    } else {
      Short requestSeq = (short) random.nextInt();
      ByteBufTools.writeShort(out, requestSeq);
    }
    ByteBufTools.writeShort(out, msg.getMessage().getMessageId());
    ByteBufTools.writeShort(out, msg.getMessage().getVersion());
    ByteBufTools.writeInteger(out, subBuf.writerIndex());
    ByteBufTools.writeBytes(out, subBuf.array(), 0, subBuf.writerIndex());


    File attach = new File(msg.getAttachment());

    MessageDigest MD5 = MessageDigest.getInstance("MD5");
    FileInputStream fileInputStream = new FileInputStream(attach);
    byte[] buffer = new byte[8192];
    int length;
    while ((length = fileInputStream.read(buffer)) != -1) {
      MD5.update(buffer, 0, length);

    }
    String md5 = new String(Hex.encodeHex(MD5.digest()));


  }*/


}
