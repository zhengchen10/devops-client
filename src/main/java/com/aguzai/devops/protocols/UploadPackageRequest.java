package com.aguzai.devops.protocols;

import com.aguzai.devops.common.kernal.message.BaseRequest;
import com.aguzai.devops.common.kernal.message.ByteBufTools;
import io.netty.buffer.ByteBuf;

public class UploadPackageRequest extends BaseRequest {
  private String taskId;
  private byte[] datas;
  private int start;
  private int length;
  public UploadPackageRequest(){
    super.setMessageId(MessageConst.UPLOAD_FILE_PACKAGE);
    super.setVersion((short)1);
  }

  public byte[] getDatas() {
    return datas;
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

  public void setDatas(byte[] datas) {
    this.datas = datas;
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
    ByteBufTools.writeBytes(resp,datas,0,length);
  }
}
