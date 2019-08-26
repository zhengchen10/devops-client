package com.aguzai.devops.protocols;

import com.aguzai.devops.common.kernal.message.BaseMessage;
import com.aguzai.devops.common.kernal.message.BaseRequest;
import com.aguzai.devops.common.kernal.message.ByteBufTools;
import io.netty.buffer.ByteBuf;

public class LinkFileRequest extends BaseRequest {
  private String src = "";
  private String dest = "";
  public LinkFileRequest(){
    super.setMessageId(MessageConst.LINK_FILE_REQUEST);
    super.setVersion((short)1);
  }
  public void fromBytes(ByteBuf byteBuf, short messageId, short version) {
    this.src = ByteBufTools.readString(byteBuf);
    this.dest = ByteBufTools.readString(byteBuf);
  }

  public String getDest() {
    return dest;
  }

  public String getSrc() {
    return src;
  }

  public void setDest(String dest) {
    this.dest = dest;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public void toBytes(ByteBuf resp) {
    ByteBufTools.writeString(resp,src);
    ByteBufTools.writeString(resp,dest);
  }
}
