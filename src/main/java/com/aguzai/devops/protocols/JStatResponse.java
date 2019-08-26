package com.aguzai.devops.protocols;

import com.aguzai.devops.common.kernal.message.BaseResponse;
import com.aguzai.devops.common.kernal.message.ByteBufTools;
import io.netty.buffer.ByteBuf;

public class JStatResponse extends BaseResponse {
  private float s0;
  private float s1;
  private float e;
  private float o;
  private float m;
  private int ygc;
  private float ygct;
  private int fgc;
  private float fgct;
  private float gct;

  @Override
  public void fromBytes(ByteBuf byteBuf, short messageId, short version) {
    super.fromBytes(byteBuf,messageId,version);
    if(isSuccess()){
      int len = ByteBufTools.readInteger(byteBuf);
      s0 = ByteBufTools.readFloat(byteBuf);
      s1 = ByteBufTools.readFloat(byteBuf);
      e  = ByteBufTools.readFloat(byteBuf);
      o  = ByteBufTools.readFloat(byteBuf);
      m  = ByteBufTools.readFloat(byteBuf);
      ygc  = ByteBufTools.readInteger(byteBuf);
      ygct  = ByteBufTools.readFloat(byteBuf);
      fgc  = ByteBufTools.readInteger(byteBuf);
      fgct  = ByteBufTools.readFloat(byteBuf);
      gct  = ByteBufTools.readFloat(byteBuf);
    }
  }
}
