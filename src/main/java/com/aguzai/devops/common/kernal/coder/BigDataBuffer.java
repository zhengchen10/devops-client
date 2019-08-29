package com.aguzai.devops.common.kernal.coder;

import com.aguzai.devops.common.kernal.message.BaseRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class BigDataBuffer {
  private ByteBuf bigDataBuf = null;
  private short requestSeq ;
  private short messageId ;
  private short version ;
  private int length ;
  private int receiveLength;

  public BigDataBuffer(short requestSeq,short messageId,short version,int length){
    this.requestSeq = requestSeq;
    this.messageId = messageId;
    this.version = version;
    this.length = length;
    bigDataBuf = Unpooled.buffer(length);
  }
  public ByteBuf getBigDataBuf() {
    return bigDataBuf;
  }

  public int getLength() {
    return length;
  }

  public short getMessageId() {
    return messageId;
  }

  public int getReceiveLength() {
    return receiveLength;
  }

  public short getRequestSeq() {
    return requestSeq;
  }

  public short getVersion() {
    return version;
  }

  public void setBigDataBuf(ByteBuf bigDataBuf) {
    this.bigDataBuf = bigDataBuf;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public void setMessageId(short messageId) {
    this.messageId = messageId;
  }

  public void setReceiveLength(int receiveLength) {
    this.receiveLength = receiveLength;
  }

  public void setRequestSeq(short requestSeq) {
    this.requestSeq = requestSeq;
  }

  public void setVersion(short version) {
    this.version = version;
  }

  public boolean appendData(ByteBuf data){
      bigDataBuf.writeBytes(data);
      return bigDataBuf.writerIndex() == length;
  }
}
