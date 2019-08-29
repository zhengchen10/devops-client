package com.aguzai.devops.protocols;

import com.aguzai.devops.common.kernal.message.BaseRequest;
import com.aguzai.devops.common.kernal.message.ByteBufTools;
import io.netty.buffer.ByteBuf;

public class UploadRequest extends BaseRequest {
  private String attachment;
  private String fileVersion ="1";
  private String group ="";
  private String fileName ="";
  private Integer fileLength =0;
  private Integer start =0;
  public UploadRequest(){
    super.setMessageId(MessageConst.UPLOAD_FILE_REQUEST);
    super.setVersion((short)1);
  }
  public UploadRequest(UploadRequest u){
    this();
    this.attachment = u.attachment;
    this.fileVersion = u.fileVersion;
    this.group = u.group;
    this.fileName = u.fileName;
    this.fileLength = u.fileLength;
    this.start = u.start;

  }

  public void fromBytes(ByteBuf byteBuf, short messageId, short version) {

  }

  @Override
  public String getAttachment() {
    return attachment;
  }

  public Integer getFileLength() {
    return fileLength;
  }

  public String getFileName() {
    return fileName;
  }

  public String getFileVersion() {
    return fileVersion;
  }

  public String getGroup() {
    return group;
  }

  public Integer getStart() {
    return start;
  }

  @Override
  public void setAttachment(String attachment) {
    this.attachment = attachment;
  }

  public void setFileLength(Integer fileLength) {
    this.fileLength = fileLength;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setFileVersion(String fileVersion) {
    this.fileVersion = fileVersion;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public void toBytes(ByteBuf resp) {
    ByteBufTools.writeString(resp,fileName);
    ByteBufTools.writeString(resp,fileVersion);
    ByteBufTools.writeString(resp,group);
    ByteBufTools.writeInteger(resp,start);
    ByteBufTools.writeInteger(resp,fileLength);
  }
}
