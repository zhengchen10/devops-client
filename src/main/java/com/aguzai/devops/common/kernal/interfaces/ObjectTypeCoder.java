package com.aguzai.devops.common.kernal.interfaces;

/**
 * Created by zhengchen on 2017/8/26.
 */
public interface ObjectTypeCoder {
    String encodeType(String type);
    String decodeType(String type);
}
