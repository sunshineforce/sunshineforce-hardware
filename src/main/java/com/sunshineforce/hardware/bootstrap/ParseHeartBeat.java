package com.sunshineforce.hardware.bootstrap;

import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.util.ByteUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ParseHeartBeat {
    public List<Braceletdata> parse(byte[] buf, String probeMac) {
        List<Braceletdata> braceletdataList = new ArrayList<>();
        //取长度
        byte[] lenByte = new byte[2];
        System.arraycopy(buf, 5, lenByte, 0, lenByte.length);
        int length = Integer.parseInt(ByteUtil.ByteToHex(lenByte), 16);//16进制转10进制
        System.out.println(length);

        Braceletdata braceletdata = new Braceletdata();
        braceletdata.setHeartRate(1);
        braceletdataList.add(braceletdata);
        return braceletdataList;
    }

    private ParseHeartBeat() {

    }

    private static class Singleton {
        private static ParseHeartBeat parseBracelete = new ParseHeartBeat();
    }

    public static ParseHeartBeat getInstance() {
        return Singleton.parseBracelete;
    }
}
