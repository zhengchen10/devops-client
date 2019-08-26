package com.aguzai.devops.test;

import com.aguzai.devops.client.AgentClient;
import com.aguzai.devops.common.kernal.Global;
import com.aguzai.devops.common.kernal.message.BaseMessage;
import com.aguzai.devops.common.kernal.message.BaseResponse;
import com.aguzai.devops.protocols.JStatRequest;
import com.aguzai.devops.protocols.JStatResponse;
import com.aguzai.devops.protocols.LinkFileRequest;
import com.aguzai.devops.protocols.LinkFileResponse;

public class TestClient {
  public static void main(String args[]) {
    Global.registerMessageClass(10000,1,1,LinkFileRequest.class);
    Global.registerMessageClass(10000,1,2, LinkFileResponse.class);
    Global.registerMessageClass(10001,1,1,JStatRequest.class);
    Global.registerMessageClass(10001,1,2, JStatResponse.class);
    AgentClient client = new AgentClient("192.168.6.107",9080);
    client.start();
    /*LinkFileRequest req = new LinkFileRequest();
    req.setSrc("1243");
    req.setDest("1aaa");*/
    JStatRequest req = new JStatRequest();
    req.setPid("4430");
    BaseResponse resp =  client.sendSyncMessage(req);
  }
}
