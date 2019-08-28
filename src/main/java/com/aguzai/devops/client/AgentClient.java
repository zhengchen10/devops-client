package com.aguzai.devops.client;

import com.aguzai.devops.common.kernal.coder.AttachmentEncoder;

import com.aguzai.devops.common.kernal.coder.BaseMessageDecoder;
import com.aguzai.devops.common.kernal.coder.BaseMessageEncoder;
import com.aguzai.devops.common.kernal.message.BaseMessage;
import com.aguzai.devops.common.kernal.message.BaseRequest;
import com.aguzai.devops.common.kernal.message.BaseResponse;
import com.aguzai.devops.common.kernal.message.MessageWithAttachment;
import com.aguzai.devops.protocols.UploadPackage;
import com.aguzai.devops.protocols.UploadPackageResult;
import com.aguzai.devops.protocols.UploadRequest;
import com.aguzai.devops.protocols.UploadResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.ConcurrentSet;

import java.io.*;
import java.nio.channels.ClosedChannelException;
import java.util.Iterator;
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
    private ConcurrentHashMap listenerMap = new ConcurrentHashMap();
    public AgentClient(String server, int port)
    {
        this.serverHost = server;
        this.serverPort =port;

    }

    public void start() {
        channel = null;
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
                    Object o = listenerMap.remove(reqSeq);
                    if(o != null && o instanceof  IClientListener){
                        ((IClientListener)o).onReceiveMessage(context,message);
                    }
                }
            }
        }

        public void onConnectionClosed(ChannelHandlerContext context) {
            Iterator iter = AgentClient.this.checkSet.iterator();
            while (iter.hasNext()){
                Object item = iter.next();
                Short key = (Short)item;
                AgentClient.this.callbackMap.put(key,new Object());
            }
            Iterator iter1 = AgentClient.this.listenerMap.values().iterator();
            while (iter1.hasNext()){
                IClientListener o = (IClientListener)iter1.next();
                o.onConnectionClosed(context);
            }
            checkSet.clear();
        }
    };

    public void sendAsyncMessage(BaseRequest request,IClientListener listener) {

        short req = BaseRequest.newRequestSeq();

        while (listenerMap.putIfAbsent(req,listener ) != null){
            req = BaseRequest.newRequestSeq();
        }
        request.setRequestSeq(req);
        System.out.println("sendAsyncMessage :" +req);
        try {
            channel.writeAndFlush(request).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void uploadFile(final UploadRequest m){

        try {
            final RandomAccessFile file =new RandomAccessFile(m.getAttachment(), "r");

            //final FileInputStream fileStream = new FileInputStream(m.getAttachment());
            final int packageLength = 819200;
            final byte[] buffer = new byte[packageLength];
            //File file = new File(m.getAttachment());
            final long fileLength = file.length();
            final int start = m.getStart();
            m.setFileLength((int)fileLength);

            IClientListener uploadCallback = new IClientListener() {
                public void onReceiveMessage(ChannelHandlerContext context, BaseMessage message) {
                    final String taskId = ((UploadResponse)message).getTaskId();
                    new Thread(new Runnable() {
                        public void run() {
                            boolean failed =false;
                            long i = start;
                            for(;i<fileLength;i+=packageLength){
                                try {
                                    long length = Math.min(packageLength,fileLength - i);
                                    file.seek(i);
                                    file.read(buffer,0,(int)length);

                                    UploadPackage up = new UploadPackage();
                                    up.setDatas(buffer);
                                    up.setStart((int)i);
                                    up.setTaskId(taskId);
                                    up.setLength((int)length);
                                    //System.out.println("Send package "+i);
                                    Object result = AgentClient.this.sendSyncMessage(up);
                                    if(result == null){
                                        failed = true;
                                        break;
                                    }
                                } catch (IOException e) {
                                    if( e instanceof ClosedChannelException){
                                        listener.onConnectionClosed(null);
                                        failed = true;
                                        break;
                                    }
                                    e.printStackTrace();
                                }
                            }
                            AgentClient.this.channel.close();
                            if(failed){
                                AgentClient.this.start();
                                UploadRequest req = new UploadRequest();
                                req.setFileName(m.getFileName());
                                req.setFileVersion(m.getFileVersion());
                                req.setAttachment(m.getAttachment());
                                req.setGroup(m.getGroup());
                                req.setStart((int)i);
                                req.setFileLength(m.getFileLength());
                                AgentClient.this.uploadFile(req);
                            }
                        }
                    }).start();
                }

                public void onConnectionClosed(ChannelHandlerContext context) {

                }
            };

            sendAsyncMessage(m, uploadCallback);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (  IOException e){
            e.printStackTrace();
        }

        /*try {
            channel.writeAndFlush(new MessageWithAttachment(m,m.getAttachment())).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
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
        Object obj = callbackMap.remove(req);
        checkSet.remove(req);
        if( obj instanceof  BaseResponse){
            return (BaseResponse) obj;
        }else {
            return null;
        }
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
