package com.aguzai.devops.client;


import com.aguzai.devops.common.kernal.message.BaseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zhengchen on 2017/8/4.
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<BaseMessage> {
    IClientListener listener;
    public TcpClientHandler(IClientListener listener){

        this.listener = listener;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMessage message) throws Exception {
        listener.onReceiveMessage(channelHandlerContext,message);
    }

    public static void sendPackage(ChannelHandlerContext ctx, BaseMessage message){
        try {
            ctx.channel().writeAndFlush(message).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {        // TODO Auto-generated method stub
        super.handlerRemoved(ctx);
        this.listener.onConnectionClosed(ctx);
    }

}
