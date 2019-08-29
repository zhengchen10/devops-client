package com.aguzai.devops.protocols;

import com.aguzai.devops.common.kernal.message.BaseRequest;
import com.aguzai.devops.common.kernal.message.ByteBufTools;
import io.netty.buffer.ByteBuf;

public class DownloadRequest extends BaseRequest {
  private String savePath;
  private String group ="";
  private String fileName ="";
  private boolean compress = false;
  private Integer start =0;
  public DownloadRequest(){
    super.setMessageId(MessageConst.DOWNLOAD_FILE_REQUEST);
    super.setVersion((short)1);
  }
  public DownloadRequest(DownloadRequest d){
    this();
    this.savePath = d.savePath;
    this.group = d.group;
    this.fileName = d.fileName;
    this.compress = d.compress;
    this.start = d.start;
    super.setMessageId(MessageConst.DOWNLOAD_FILE_REQUEST);
    super.setVersion((short)1);
  }

  public String getFileName() {
    return fileName;
  }

  public String getGroup() {
    return group;
  }

  public String getSavePath() {
    return savePath;
  }
  public int getStart() {
    return start;
  }

  public boolean isCompress() {
    return compress;
  }

  public void setCompress(boolean compress) {
    this.compress = compress;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public void setSavePath(String savePath) {
    this.savePath = savePath;
  }
  public void setStart(int start) {
    this.start = start;
  }

  public void toBytes(ByteBuf resp) {
    ByteBufTools.writeString(resp,fileName);
    ByteBufTools.writeString(resp,group);
    ByteBufTools.writeBoolean(resp,compress);
    ByteBufTools.writeInteger(resp,start);
  }
}
