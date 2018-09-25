package com.sunshineforce.hardware.bootstrap;

import com.sunshineforce.hardware.util.ByteUtil;
import com.sunshineforce.hardware.util.UdpUtil;

import java.net.UnknownHostException;

public class HeartBeat {
    private HeartBeat() {
    }

    private static class Singleton{
        private static HeartBeat heartBeat = new HeartBeat();
    }
    public static HeartBeat getInstance(){
        return Singleton.heartBeat;
    }

    public boolean heartBeat(String probeMac) throws UnknownHostException{
        MessageUtil.sendMessage(183, 10, UdpUtil.macTpHex(probeMac), UdpUtil.ipTpHex(UdpUtil.getLocalIp()));
        while (true){
            byte[] buf = UdpUtil.getUdpResponse(3333);
            System.out.println(ByteUtil.ByteToHex(buf));
            if(buf.length > 0){
                return true;
            }
        }
    }
}
