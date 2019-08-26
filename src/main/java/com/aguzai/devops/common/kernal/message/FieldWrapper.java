package com.aguzai.devops.common.kernal.message;

/**
 * Created by zhengchen on 2016/12/19.
 */
public class FieldWrapper {
    private String name;
    private String type;
    private Object value = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

