package com.aguzai.devops.common.kernal.message;

import io.netty.buffer.ByteBuf;

import java.util.Random;

public abstract class BaseRequest extends BaseMessage{
  private static Random random = new Random();
  public static short newRequestSeq(){
    return (short)random.nextInt();
  }
  private short requestSeq;

  public String getAttachment() {
    return attachment;
  }

  public short getRequestSeq() {
    return requestSeq;
  }

  public void setAttachment(String attachment) {
    this.attachment = attachment;
  }

  public void setRequestSeq(short requestSeq) {
    this.requestSeq = requestSeq;
  }

  private String attachment = null;
}
