package com.aguzai.devops.common.kernal;


import com.aguzai.devops.common.kernal.interfaces.MessageSender;
import com.aguzai.devops.common.kernal.interfaces.ObjectTypeCoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengchen on 2017/8/14.
 */
public class Global {
    protected static Map properties = new HashMap();
    static Map handlerMap = new HashMap();
    static Map objectMap = new HashMap();
    //static ZalldsGlobalPropertyConfigurer  propertyConfigurer = null;
    static  {
        /*if(propertyConfigurer == null)
            propertyConfigurer = (ZalldsGlobalPropertyConfigurer)SpringHelper.getBean("propertyConfigurer");
        Properties plugins = propertyConfigurer.getProperties("plugins.properties");
        int start = 0;
        while (true){
            String key = "Plugin."+start;
            String pluginClass = plugins.getProperty(key);
            if(pluginClass== null || pluginClass.equals(""))
                break;
            try {
                Class clz = Class.forName(pluginClass);
                Plugin plugin = (Plugin)clz.newInstance();
                plugin.init();
                System.out.println("* Init Plugin "+ pluginClass);
            }catch (Exception exp){

            }
            start++;
        }*/
        //registerMessageClass(10002,1, LinkFileResponse.class);
    }

    public static Class getMessageClass(int messageId, int version,int msgType){
        return (Class)objectMap.get(messageId+"."+version+"."+msgType);
    }

    public static void registerMessageClass(int messageId, int version, int msgType,Class clz){
        objectMap.put(messageId+"."+version+"."+msgType,clz);
    }

    private static boolean tcpServer = false;
    public  static boolean isTcpServer() {return  tcpServer;}
    public static void init(boolean tcpServer) {
        Global.tcpServer = tcpServer;
    }

    private static MessageSender sender;
    public static MessageSender getMessageSender(){
        return sender;
    }
    public static void setMessageSender(MessageSender sender){
        Global.sender = sender;
    }

    private static ObjectTypeCoder typeCoder = new DefaultTypeCoder();
    public static ObjectTypeCoder getObjectTypeCoder() {
        return typeCoder;
    }

    public void setObjectTypeCoder(ObjectTypeCoder typeCoder){
        Global.typeCoder = typeCoder;
    }
}
