package com.aguzai.devops.protocols;

import com.aguzai.devops.common.kernal.message.BaseRequest;
import com.aguzai.devops.common.kernal.message.ByteBufTools;
import io.netty.buffer.ByteBuf;

public class DownloadPackageRequest extends BaseRequest {
  private String taskId;
  private int start;
  private int length;
  public DownloadPackageRequest(){
    super.setMessageId(MessageConst.DOWNLOAD_PACKAGE_REQUEST);
    super.setVersion((short)1);
  }

  public int getLength() {
    return length;
  }

  public int getStart() {
    return start;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public void toBytes(ByteBuf resp) {
    ByteBufTools.writeString(resp,taskId);
    ByteBufTools.writeInteger(resp,start);
    ByteBufTools.writeInteger(resp,length);
  }
}
