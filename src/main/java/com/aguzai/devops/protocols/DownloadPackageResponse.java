package com.aguzai.devops.protocols;

import com.aguzai.devops.common.kernal.message.BaseResponse;
import com.aguzai.devops.common.kernal.message.ByteBufTools;
import io.netty.buffer.ByteBuf;

public class DownloadPackageResponse extends BaseResponse {
  private Integer start;
  private Integer length;
  private byte[] data;

  public byte[] getData() {
    return data;
  }

  public Integer getLength() {
    return length;
  }

  public Integer getStart() {
    return start;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  @Override
  public void fromBytes(ByteBuf byteBuf, short messageId, short version) {
    super.fromBytes(byteBuf,messageId,version);
    if(isSuccess()){
      int len = ByteBufTools.readInteger(byteBuf);
      start = ByteBufTools.readInteger(byteBuf);
      length = ByteBufTools.readInteger(byteBuf);
      data = ByteBufTools.readBytes(byteBuf,length);
    }
  }
}
