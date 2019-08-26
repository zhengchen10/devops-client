package com.aguzai.devops.common.kernal;

import com.aguzai.devops.common.kernal.interfaces.ObjectTypeCoder;

/**
 * Created by zhengchen on 2017/8/26.
 */
public class DefaultTypeCoder implements ObjectTypeCoder {
    public String encodeType(String type) {
        return type;
    }

    public String decodeType(String type) {
        return type;
    }
}
