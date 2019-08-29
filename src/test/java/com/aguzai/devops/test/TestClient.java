package com.aguzai.devops.test;

import com.aguzai.devops.client.AgentClient;
import com.aguzai.devops.client.IClientListener;
import com.aguzai.devops.client.IProgressListener;
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
    Global.registerMessageClass(10003,1,1,UploadPackageRequest.class);
    Global.registerMessageClass(10003,1,2, UploadPackageResponse.class);
    Global.registerMessageClass(10004,1,1,DownloadRequest.class);
    Global.registerMessageClass(10004,1,2, DownloadResponse.class);
    Global.registerMessageClass(10005,1,1,DownloadPackageRequest.class);
    Global.registerMessageClass(10005,1,2, DownloadPackageResponse.class);
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
    /*client.uploadFile(u, new IProgressListener() {
      public void onProgress(int s) {
        if(s == 99){
          System.out.println("send :"+ s +"%" );
        }else {
          System.out.println("send :"+ s +"%" );
        }
      }
    });*/
    DownloadRequest d = new DownloadRequest();
    d.setFileName("nexus-2.14.5-02-bundle.zip");
    d.setGroup("test");
    d.setCompress(false);
    d.setSavePath("D:\\upload\\download\\");
    d.setStart(0);
    client.downloadFile(d, new IProgressListener() {
      public void onProgress(int s) {
        System.out.println("send :"+ s +"%" );
      }
    });
    /*client.sendAsyncMessage(u, new IClientListener() {
      public void onReceiveMessage(ChannelHandlerContext context, BaseMessage message) {

      }
    });*/
  }
}
