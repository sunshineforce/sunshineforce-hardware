package com.sunshineforce.hardware.bootstrap;

import com.sunshineforce.hardware.util.UdpUtil;

import java.net.UnknownHostException;

public class MessageUpload {

    private static final String header = "ad";
    private static final int pd = 1;

    public void getMessage(String probeMac ,int code) throws UnknownHostException {
        switch (code){
            case 183 : HeartBeat.getInstance().heartBeat(probeMac);
                break;
        }
    }
}
