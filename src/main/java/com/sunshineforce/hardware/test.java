package com.sunshineforce.hardware;

import com.sunshineforce.hardware.util.ByteUtil;
import com.sunshineforce.hardware.util.UdpUtil;
import org.apache.commons.lang3.StringUtils;

public class test {

    private static String message = "ad ba 00 01 01 00 51 00 00 00 00 ca aa 16 14 07 73 00 01 d8 05 61 2a 14 f4 b7 02 01 06 03 03 0d 18 16 ff b8 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 27 9f 00 0b ff b6 04 48 b7 d8 05 61 2a 14 f4 10 09 52 37 5f 64 38 30 35 36 31 32 61 31 34 66 34 00 00 1f c8 31 39 37 30 2d 30 31 2d 30 31 20 30 30 3a 34 34 3a 33 30";

    public static void main(String args[]){
        System.out.println(StringUtils.replace(message, " ", ""));
        while(true){
            UdpUtil.sendUdpRequest(ByteUtil.HexToByte(StringUtils.replace(message, " ", "")));
        }
    }
}
