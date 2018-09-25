package com.sunshineforce.hardware.bootstrap;

import com.sunshineforce.hardware.util.ByteUtil;
import com.sunshineforce.hardware.util.UdpUtil;

public class MessageUtil {

    private static final Integer header = 173;
    private static final Integer pd = 1;

    public static void sendMessage(int type, int length, Object ...args){
        StringBuilder builder = new StringBuilder();
        builder.append(header).append(pd).append(type).append(length);
        for(int i = 0; i < args.length; i++){
            builder.append(args[i]);
        }
        System.out.println(builder.toString());
        byte[] message = ByteUtil.strToHex(builder.toString()).getBytes();
        UdpUtil.sendUdpRequest(message);
    }
}
