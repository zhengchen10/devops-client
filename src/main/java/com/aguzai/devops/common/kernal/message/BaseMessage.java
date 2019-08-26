package com.aguzai.devops.common.kernal.message;

import io.netty.buffer.ByteBuf;

public abstract class BaseMessage {
  private short messageId;
  private short version;

  public abstract void fromBytes(ByteBuf byteBuf, short messageId, short version) ;

  public short getMessageId() {
    return messageId;
  }

  public short getVersion() {
    return version;
  }

  public void setMessageId(short messageId) {
    this.messageId = messageId;
  }

  public void setVersion(short version) {
    this.version = version;
  }

  public abstract void toBytes(ByteBuf resp) ;
}
