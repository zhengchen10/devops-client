package com.aguzai.devops.common.kernal.interfaces;

/**
 * Created by zhengchen on 2017/11/8.
 */
public interface MessageAckSender {
    void sendAck(int seq, int item);
}
