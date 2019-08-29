package com.aguzai.devops.common.kernal.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by zhengchen on 2016/12/19.
 */
public class ByteBufTools {
    public static boolean writeString(ByteBuf buf, String str){
        if(str == null) str = "";
        try {
            byte[] buffer = str.getBytes("utf-8");
            Integer length = buffer.length;
            buf.writeShort(length);
            if(length >0)
                buf.writeBytes(buffer);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void writeCustomWithName(ByteBuf out, String str, String name){
        if(name != null)
            ByteBufTools.writeString(out,name);
        ByteBufTools.writeString(out,"ct");
        ByteBufTools.writeString(out,str);
    }

    public static void writeStringWithName(ByteBuf out, String str, String name){
        if(name != null)
            ByteBufTools.writeString(out,name);
        if(str == null){
            ByteBufTools.writeString(out,"n");
        } else {
            ByteBufTools.writeString(out,"s");
            ByteBufTools.writeString(out,str);
        }
    }

    public static boolean writeShort(ByteBuf buf, Short s){
        if(s==null)
            s = 0;
        buf.writeShort(s);
        return true;
    }
    public static void writeShortWithName(ByteBuf out, Short i, String name){
        if(name != null)
            ByteBufTools.writeString(out,name);
        if(i == null){
            ByteBufTools.writeString(out,"n");
        } else {
            ByteBufTools.writeString(out,"t");
            out.writeShort(i);
        }
    }

    public static boolean writeInteger(ByteBuf buf, Integer i){
        if(i == null)
            i=0;
        buf.writeInt(i);
        return true;
    }

    public static void writeIntegerWithName(ByteBuf out, Integer i, String name){
        if(name != null)
            ByteBufTools.writeString(out,name);
        if(i == null){
            ByteBufTools.writeString(out,"n");
        } else {
            ByteBufTools.writeString(out,"i");
            out.writeInt(i);
        }
    }

    public static boolean writeLong(ByteBuf buf, Long v){
        if(v == null)
            v = 0L;
        buf.writeLong(v);
        return true;
    }

    public static void writeLongWithName(ByteBuf out, Long v, String name){
        if(name != null)
            ByteBufTools.writeString(out,name);
        if(v == null){
            ByteBufTools.writeString(out,"n");
        } else {
            ByteBufTools.writeString(out,"l");
            out.writeLong(v);
        }
    }

    public static boolean writeBoolean(ByteBuf buf, Boolean v){
        if(v == null)
            v = false;
        buf.writeBoolean(v);
        return true;
    }

    public static void writeBooleanWithName(ByteBuf out, Boolean v, String name){
        if(name != null)
            ByteBufTools.writeString(out,name);
        if(v == null){
            ByteBufTools.writeString(out,"n");
        } else {
            ByteBufTools.writeString(out,"b");
            out.writeBoolean(v);
        }
    }

    public static boolean writeFloat(ByteBuf buf, Float v){
        if(v == null)
            v = 0f;
        buf.writeFloat(v);
        return true;
    }

    public static void writeFloatWithName(ByteBuf out, Float v, String name){
        if(name != null)
            ByteBufTools.writeString(out,name);
        if(v == null){
            ByteBufTools.writeString(out,"n");
        } else {
            ByteBufTools.writeString(out,"f");
            out.writeFloat(v);
        }
    }

    public static boolean writeDouble(ByteBuf buf, Double v){
        if(v == null)
            v = 0.0;
        buf.writeDouble(v);
        return true;
    }

    public static void writeDoubleWithName(ByteBuf out, Double v, String name){
        if(name != null)
            ByteBufTools.writeString(out,name);
        if(v == null){
            ByteBufTools.writeString(out,"n");
        } else {
            ByteBufTools.writeString(out,"d");
            out.writeDouble(v);
        }
    }

    public static void writeDateWithName(ByteBuf out, Date date, String name) {
        if(name != null)
            ByteBufTools.writeString(out,name);
        if(date == null){
            ByteBufTools.writeString(out,"n");
        } else {
            ByteBufTools.writeString(out,"dt");
            out.writeLong(date.getTime());
        }
    }



    public static String readString(ByteBuf buf){
        Short length = buf.readShort();

        if(length == 0){
            return "";
        } else {
            byte[] buffer = new byte[length];
            buf.readBytes(buffer);
            try {
                String str = new String(buffer,"utf-8");
                return str;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return  null;
        }
    }

    public static FieldWrapper readStringWithName(ByteBuf buf,boolean readName){
        FieldWrapper fw = new FieldWrapper();
        if(readName)
            fw.setName(readString(buf));
        String type = readString(buf);
        if(type.equals("n"))
            return fw;
        fw.setValue(readString(buf));
        return fw;
    }

    public static String readCustomWithName(ByteBuf buf, boolean readName){
        if(readName)
            readString(buf);
        String type = readString(buf);
        String value = readString(buf);
        return value;
    }

    public static Integer readInteger(ByteBuf buf) {
        Integer i = buf.readInt();
        return i;
    }

    public static FieldWrapper readIntegerWithName(ByteBuf buf,boolean readName){
        FieldWrapper fw = new FieldWrapper();
        if(readName)
            fw.setName(readString(buf));
        String type = readString(buf);
        if(type.equals("n"))
            return fw;
        fw.setValue(readInteger(buf));
        return fw;
    }

    public static Short readShort(ByteBuf buf) {
        Short i = buf.readShort();
        return i;
    }

    public static FieldWrapper readShortWithName(ByteBuf buf,boolean readName){
        FieldWrapper fw = new FieldWrapper();
        if(readName)
            fw.setName(readString(buf));
        String type = readString(buf);
        if(type.equals("n"))
            return fw;
        fw.setValue(readShort(buf));
        return fw;
    }


    public static Long readLong(ByteBuf buf) {
        Long i = buf.readLong();
        return i;
    }

    public static FieldWrapper readLongWithName(ByteBuf buf,boolean readName){
        FieldWrapper fw = new FieldWrapper();
        if(readName)
            fw.setName(readString(buf));
        String type = readString(buf);
        if(type.equals("n"))
            return fw;
        fw.setValue(readLong(buf));
        return fw;
    }

    public static Float readFloat(ByteBuf buf) {
        Float i = buf.readFloat();
        return i;
    }

    public static FieldWrapper readFloatWithName(ByteBuf buf,boolean readName){
        FieldWrapper fw = new FieldWrapper();
        if(readName)
            fw.setName(readString(buf));
        String type = readString(buf);
        if(type.equals("n"))
            return fw;
        fw.setValue(readFloat(buf));
        return fw;
    }

    public static Double readDouble(ByteBuf buf) {
        Double i = buf.readDouble();
        return i;
    }

    public static FieldWrapper readDoubleWithName(ByteBuf buf,boolean readName){
        FieldWrapper fw = new FieldWrapper();
        if(readName)
            fw.setName(readString(buf));
        String type = readString(buf);
        if(type.equals("n"))
            return fw;
        fw.setValue(readDouble(buf));
        return fw;
    }

    public static boolean readBoolean(ByteBuf buf){
        return  buf.readBoolean();
    }

    public static FieldWrapper readBooleanWithName(ByteBuf buf,boolean readName){
        FieldWrapper fw = new FieldWrapper();
        if(readName)
            fw.setName(readString(buf));
        String type = readString(buf);
        if(type.equals("n"))
            return fw;
        fw.setValue(readBoolean(buf));
        return fw;
    }

    public static FieldWrapper readDateWithName(ByteBuf buf, boolean readName) {
        FieldWrapper fw = new FieldWrapper();
        if(readName)
            fw.setName(readString(buf));
        String type = readString(buf);
        if(type.equals("n"))
            return fw;
        Date date = new Date();
        date.setTime(readLong(buf));
        fw.setValue(date);
        return fw;
    }

    public static Date readDate(ByteBuf out) {
        Long i = out.readLong();
        Date date = new Date();
        date.setTime(i);
        return date;
    }


    public static boolean writeDate(ByteBuf out, Date date) {
        if(date == null) {
            date = new Date();
            date.setTime(0);
        }
        out.writeLong(date.getTime());
        return true;
    }

    public static ByteBuf getByteBuf(BaseMessage message){
        ByteBuf resp= Unpooled.buffer(1024);
        try {
            message.toBytes(resp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resp;
    }

    public static byte[] readBytes(ByteBuf out,int length){
        byte[] ret = new byte[length];
        out.getBytes(out.readerIndex(),ret,0,length);
        return ret;
    }

    public static void writeBytes(ByteBuf out, byte[] buffer, int offset, int length) {
        out.writeBytes(buffer,offset,length);
    }
}
