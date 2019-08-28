package com.aguzai.devops.test;

import com.aguzai.devops.client.AgentClient;
import com.aguzai.devops.client.IClientListener;
import com.aguzai.devops.common.kernal.Global;
import com.aguzai.devops.common.kernal.message.BaseMessage;
import com.aguzai.devops.common.kernal.message.BaseResponse;
import com.aguzai.devops.common.kernal.message.MessageWithAttachment;
import com.aguzai.devops.protocols.*;
import io.netty.channel.ChannelHandlerContext;

public class TestClient {
  public static void main(String args[]) {
    Global.registerMessageClass(10000,1,1,LinkFileRequest.class);
    Global.registerMessageClass(10000,1,2, LinkFileResponse.class);
    Global.registerMessageClass(10001,1,1,JStatRequest.class);
    Global.registerMessageClass(10001,1,2, JStatResponse.class);
    Global.registerMessageClass(10002,1,1,UploadRequest.class);
    Global.registerMessageClass(10002,1,2, UploadResponse.class);
    Global.registerMessageClass(10003,1,1,UploadPackage.class);
    Global.registerMessageClass(10003,1,2, UploadPackageResult.class);
   // AgentClient client = new AgentClient("192.168.6.107",9080);
    AgentClient client = new AgentClient("127.0.0.1",9080);
    client.start();
    /*LinkFileRequest req = new LinkFileRequest();
    req.setSrc("1243");
    req.setDest("1aaa");*/
    JStatRequest req = new JStatRequest();
    req.setPid("4430");
    UploadRequest u = new UploadRequest();
    u.setAttachment("d:\\nexus-2.14.5-02-bundle.zip");
    u.setFileName("nexus-2.14.5-02-bundle.zip");
    u.setFileVersion("1.0");
    u.setGroup("test");
    client.uploadFile(u);
    /*client.sendAsyncMessage(u, new IClientListener() {
      public void onReceiveMessage(ChannelHandlerContext context, BaseMessage message) {

      }
    });*/
  }
}
