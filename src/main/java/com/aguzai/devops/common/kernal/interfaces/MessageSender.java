package com.aguzai.devops.common.kernal.interfaces;

import com.aguzai.devops.common.kernal.message.BaseMessage;
import io.netty.channel.ChannelHandlerContext;

import java.net.SocketAddress;

/**
 * Created by zhengchen on 2017/8/14.
 */
public interface MessageSender {
    void sendMessage(ChannelHandlerContext ctx, SocketAddress sender, BaseMessage message);
}
