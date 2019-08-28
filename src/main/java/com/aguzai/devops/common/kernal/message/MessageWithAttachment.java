package com.aguzai.devops.common.kernal.message;

public class MessageWithAttachment {
    private BaseMessage message;
    private String attachment;
    public MessageWithAttachment(BaseMessage message,String attachment){
      this.message = message;
      this.attachment = attachment;
    }

  public String getAttachment() {
    return attachment;
  }

  public BaseMessage getMessage() {
    return message;
  }

  public void setAttachment(String attachment) {
    this.attachment = attachment;
  }

  public void setMessage(BaseMessage message) {
    this.message = message;
  }
}
