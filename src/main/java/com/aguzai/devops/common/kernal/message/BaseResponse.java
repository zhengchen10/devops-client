package com.aguzai.devops.common.kernal.message;

import io.netty.buffer.ByteBuf;

public class BaseResponse extends BaseMessage{
  private boolean success;
  private short error;
  private short requestSeq;

  public void fromBytes(ByteBuf byteBuf, short messageId, short version) {
    success = ByteBufTools.readBoolean(byteBuf);
    if(!success)
      error = ByteBufTools.readShort(byteBuf);
  }

  public short getError() {
    return error;
  }

  public short getRequestSeq() {
    return requestSeq;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setError(short error) {
    this.error = error;
  }

  public void setRequestSeq(short requestSeq) {
    this.requestSeq = requestSeq;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public void toBytes(ByteBuf resp) {

  }
}
