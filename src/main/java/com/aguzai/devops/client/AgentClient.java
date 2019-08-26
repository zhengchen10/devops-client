package com.aguzai.devops.client;

import com.aguzai.devops.common.kernal.coder.BaseMessageDecoder;
import com.aguzai.devops.common.kernal.coder.BaseMessageEncoder;
import com.aguzai.devops.common.kernal.message.BaseMessage;
import com.aguzai.devops.common.kernal.message.BaseRequest;
import com.aguzai.devops.common.kernal.message.BaseResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.ConcurrentSet;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhengchen on 2017/8/4.
 */
public class AgentClient {
    private String serverHost = "127.0.0.1";
    private int serverPort = 9080;


    private Bootstrap bootstrap;
    private Channel channel = null;
    private ConcurrentSet checkSet = new ConcurrentSet();
    private ConcurrentHashMap callbackMap = new ConcurrentHashMap();
    public AgentClient(String server, int port)
    {
        this.serverHost = server;
        this.serverPort =port;

    }

    public void start() {
        new Thread(new Runnable() {
            public void run() {
                bootstrap = getBootstrap();
            }
        }).start();
        while (channel == null){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private IClientListener listener = new IClientListener() {
        public void onReceiveMessage(ChannelHandlerContext context, BaseMessage message) {
            if(message instanceof BaseResponse){
                short reqSeq = ((BaseResponse)message).getRequestSeq();
                if(checkSet.contains(reqSeq))
                    callbackMap.put(reqSeq,message);
                else {
                    Object o = callbackMap.remove(reqSeq);
                    if(o != null && o instanceof  IClientListener){
                        ((IClientListener)o).onReceiveMessage(context,message);
                    }
                }
            }
        }
    };
    /*public void sendMessage(BaseMessage request){

        try {
            channel.writeAndFlush(request).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    public void sendAsyncMessage(BaseRequest request,IClientListener listener) {
        short req = BaseRequest.newRequestSeq();
        while (callbackMap.putIfAbsent(req,listener ) != null){
            req = BaseRequest.newRequestSeq();
        }
        request.setRequestSeq(req);
        try {
            channel.writeAndFlush(request).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public BaseResponse sendSyncMessage(BaseRequest request) {
        short req = BaseRequest.newRequestSeq();
        while (checkSet.add(req) == false){
            req = BaseRequest.newRequestSeq();
        }

        request.setRequestSeq(req);
        //callbackMap.put(req,listener);
        try {
            channel.writeAndFlush(request).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (callbackMap.get(req) == null){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        BaseResponse result = (BaseResponse)callbackMap.remove(req);
        checkSet.remove(req);
        return result;
    }

    public Bootstrap getBootstrap(){
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("frameEncoder",new BaseMessageEncoder());
                pipeline.addLast("frameDecoder",new BaseMessageDecoder());
                pipeline.addLast("handler", new TcpClientHandler(listener));
            }
        });


        try {
            channel = bootstrap.connect(serverHost, serverPort).sync().channel();
        } catch (Exception e) {
            return null;
        }

       /* LoginRequest login = new LoginRequest();
        login.setUserName(config.get("username").toString());
        login.setPassWord(config.get("password").toString());

        //Thread.sleep(2000);
        // UdpClient.sendMsg(login);
        try {
            channel.writeAndFlush(login).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
//		b.option(ChannelOption.SO_KEEPALIVE, true);
        return bootstrap;
    }


    public static void main(String[] args) throws Exception {

        AgentClient client = new AgentClient("127.0.0.1",9080);
        /*LinkFileRequest req = new LinkFileRequest();
        req.setSrc("/root/1");
        req.setDest("rest/logs/");
        client.sendMessage(req);
        while (true){
            Thread.sleep(100);
        }*/

    }


}
