package com.sunshineforce.hardware;

import com.sunshineforce.hardware.util.ByteUtil;
import com.sunshineforce.hardware.util.UdpUtil;
import org.apache.commons.lang3.StringUtils;

public class test {

    //private static String message = "ad ba 00 01 01 00 51 00 00 00 00 ca aa 16 14 07 73 00 01 d8 05 61 2a 14 f4 b7 02 01 06 03 03 0d 18 16 ff b8 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 27 9f 00 0b ff b6 04 48 b7 d8 05 61 2a 14 f4 10 09 52 37 5f 64 38 30 35 36 31 32 61 31 34 66 34 00 00 1f c8 31 39 37 30 2d 30 31 2d 30 31 20 30 30 3a 34 34 3a 33 30";

    private static String message = "ad ba 00 01 01 00 db 00 00 00 00 c6 34 7c e8 68 7c 00 03 c5 93 6d 01 d2 b1 bd 02 01 06 03 03 0d 18 0b ff b6 04 48 b7 c5 93 6d 01 d2 b1 00 00 00 00 00 00 00 00 00 00 00 00 10 09 52 37 5f 63 35 39 33 36 64 30 31 64 32 62 31 00 00 00 00 00 00 00 00 00 00 00 00 00 00 cb 8a 9a dd b7 d0 c5 02 01 06 03 03 0d 18 16 ff b8 4d 00 00 00 40 00 00 00 00 00 14 6d 38 be 5b 41 00 00 2d 7d 00 0b ff b6 04 48 b7 cb 8a 9a dd b7 d0 10 09 52 37 5f 63 62 38 61 39 61 64 64 62 37 64 30 00 00 fb 7c dd 15 49 da c2 02 01 06 03 03 0d 18 16 ff b8 4f a2 01 00 00 00 00 00 00 00 14 b0 38 be 5b 4d 00 00 39 59 00 0b ff b6 04 48 b7 fb 7c dd 15 49 da 10 09 52 37 5f 66 62 37 63 64 64 31 35 34 39 64 61 00 00 b2 f5 31 39 37 30 2d 30 31 2d 30 31 20 30 36 3a 34 34 3a 33 39";
    public static void main(String args[]){
        System.out.println(StringUtils.replace(message, " ", ""));
//        while(true){
            UdpUtil.sendUdpRequest(ByteUtil.HexToByte(StringUtils.replace(message, " ", "")));
        //}
    }
}
