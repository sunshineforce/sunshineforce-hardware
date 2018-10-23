package com.sunshineforce.hardware;

import com.sunshineforce.hardware.util.ByteUtil;
import com.sunshineforce.hardware.util.UdpUtil;
import org.apache.commons.lang3.StringUtils;

public class test {

    //private static String message = "ad ba 00 01 01 00 51 00 00 00 00 ca aa 16 14 07 73 00 01 d8 05 61 2a 14 f4 b7 02 01 06 03 03 0d 18 16 ff b8 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 27 9f 00 0b ff b6 04 48 b7 d8 05 61 2a 14 f4 10 09 52 37 5f 64 38 30 35 36 31 32 61 31 34 66 34 00 00 1f c8 31 39 37 30 2d 30 31 2d 30 31 20 30 30 3a 34 34 3a 33 30";

    private static String message = "ad ba 00 01 01 00 51 00 00 00 00 e5 97 cc 95 62 a1 00 01 cf eb f6 a8 0b bd b1 02 01 06 03 03 0d 18 16 ff b8 55 c1 05 00 40 00 00 00 00 00 14 3e 6a c7 5b 41 00 00 60 94 00 0b ff b6 04 48 b7 cf eb f6 a8 0b bd 10 09 52 37 5f 63 66 65 62 66 36 61 38 30 62 62 64 00 00 db f1 31 39 37 30 2d 30 31 2d 30 31 20 30 32 3a 34 32 3a 31 35";
    public static void main(String args[]){
        //System.out.println(StringUtils.replace(message, " ", ""));
        //while(true){
            UdpUtil.sendUdpRequest(ByteUtil.HexToByte(StringUtils.replace(message, " ", "")));
        //}
    }
}
