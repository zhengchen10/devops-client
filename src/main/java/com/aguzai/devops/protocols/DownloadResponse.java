package com.aguzai.devops.protocols;

import com.aguzai.devops.common.kernal.message.BaseResponse;
import com.aguzai.devops.common.kernal.message.ByteBufTools;
import io.netty.buffer.ByteBuf;

public class DownloadResponse extends BaseResponse {
  private String filename;
  private int fileLength;
  private int start ;
  private String taskId;
  public int getFileLength() {
    return fileLength;
  }

  public String getFilename() {
    return filename;
  }

  public int getStart() {
    return start;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setFileLength(int fileLength) {
    this.fileLength = fileLength;
  }
  @Override
  public void fromBytes(ByteBuf byteBuf, short messageId, short version) {
    super.fromBytes(byteBuf,messageId,version);
    if(isSuccess()){
      int len = ByteBufTools.readInteger(byteBuf);
      taskId = ByteBufTools.readString(byteBuf);
      filename = ByteBufTools.readString(byteBuf);
      fileLength = ByteBufTools.readInteger(byteBuf);
      start = ByteBufTools.readInteger(byteBuf);
    }
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }
}
