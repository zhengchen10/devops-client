package com.aguzai.devops.client;


import com.aguzai.devops.common.kernal.message.BaseMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zhengchen on 2016/12/20.
 */
public interface IClientListener {
    public void onReceiveMessage(ChannelHandlerContext context, BaseMessage message);
}
