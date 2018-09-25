package com.sunshineforce.hardware.bootstrap;

import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.service.IProbeService;
import com.sunshineforce.hardware.util.ByteUtil;
import com.sunshineforce.hardware.util.UdpUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ParseUdp {
    private static final String HEADER = "ADBA";

    private ParseBracelete parseBracelete = ParseBracelete.getInstance();

    private ParseHeartBeat parseHeartBeat = ParseHeartBeat.getInstance();

    @Autowired
    private IProbeService iProbeService;

    public List<Braceletdata> parse(){
        DatagramSocket socket = null;
        List<Braceletdata> braceletdataList = new ArrayList<>();
        byte[] buf = UdpUtil.getUdpResponse(3333);
        byte[] header = new byte[2];
        System.arraycopy(buf, 0, header, 0, header.length); //取前两个字节
        if(!checkHeqader(header)){
            System.out.println("header false");
            return null;
        }

        //取探针设备mac
        byte[] probeMacByte = new byte[6];
        System.arraycopy(buf, 11, probeMacByte, 0, probeMacByte.length);
        String probeMac = ByteUtil.ByteToHex(probeMacByte);
        System.out.println(probeMac);
        int count = iProbeService.getProbeByMac(probeMac);
        if(count <= 0){
            System.out.println("-------------------the probe is not authorize");
            return null;
        }

        byte[] typeByte = new byte[1];
        System.arraycopy(buf, 4, typeByte, 0, typeByte.length); //取消息类型
        int type = Integer.parseInt(ByteUtil.ByteToHex(typeByte), 16);
        if(type == 1){
            braceletdataList = parseBracelete.parse(buf, probeMac);
        }else if(type == 2){
            System.out.println("-------------------type heart beat");
            braceletdataList = parseHeartBeat.parse(buf, probeMac);
        }else{
            System.out.println("type error");
            return null;
        }
        return braceletdataList;
    }

    //check header
    public boolean checkHeqader(byte[] header){
        System.out.println(ByteUtil.ByteToHex(header));
        return HEADER.equals(ByteUtil.ByteToHex(header).toUpperCase());
    }

    private ParseUdp(){
    	
    }
    
    private static class Singleton{
    	private static ParseUdp parseUdp = new ParseUdp();
    }
    
    public static ParseUdp getInstance() {
        return Singleton.parseUdp;
    }
}
