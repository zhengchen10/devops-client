package com.aguzai.devops.protocols;

import com.aguzai.devops.common.kernal.message.BaseRequest;
import com.aguzai.devops.common.kernal.message.ByteBufTools;
import io.netty.buffer.ByteBuf;

public class JStatRequest extends BaseRequest {
  private String pid ;

  public JStatRequest(){
    super.setMessageId(MessageConst.JSTAT_REQUEST);
    super.setVersion((short)1);
  }
  public void fromBytes(ByteBuf byteBuf, short messageId, short version) {

  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public void toBytes(ByteBuf resp) {
    ByteBufTools.writeString(resp,pid);
  }
}
