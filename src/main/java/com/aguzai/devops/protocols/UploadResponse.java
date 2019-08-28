package com.aguzai.devops.protocols;

import com.aguzai.devops.common.kernal.message.BaseResponse;
import com.aguzai.devops.common.kernal.message.ByteBufTools;
import io.netty.buffer.ByteBuf;

public class UploadResponse extends BaseResponse {
  private String taskId;

  private Integer start;

  public Integer getStart() {
    return start;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  @Override
  public void fromBytes(ByteBuf byteBuf, short messageId, short version) {
    super.fromBytes(byteBuf,messageId,version);
    if(isSuccess()){
      int len = ByteBufTools.readInteger(byteBuf);
      taskId = ByteBufTools.readString(byteBuf);
      start = ByteBufTools.readInteger(byteBuf);
    }
  }
}
