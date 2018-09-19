package com.sunshineforce.hardware.bootstrap;

import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.util.ByteUtil;

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

    public List<Braceletdata> parse(){
        DatagramSocket socket = null;
        List<Braceletdata> braceletdataList = new ArrayList<>();
        try{
            //建立udp的服务 ，并且要监听一个端口
            socket = new DatagramSocket(3333);

            //准备空的数据包用于存放数据
            byte[] buf = new byte[65507];

            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length); // 1024
            //调用udp的服务接收数据
            socket.receive(datagramPacket); //receive是一个阻塞型的方法，没有接收到数据包之前会一直等待。 数据实际上就是存储到了byte的自己数组中了。

            byte[] header = new byte[2];
            System.arraycopy(buf, 0, header, 0, header.length); //取前两个字节
            if(!checkHeqader(header)){
                System.out.println("header false");
                return null;
            }

            byte[] typeByte = new byte[1];
            System.arraycopy(buf, 4, typeByte, 0, typeByte.length); //取消息类型
            int type = Integer.parseInt(ByteUtil.ByteToHex(typeByte), 16);
            if(type == 1){
                braceletdataList = parseBracelete.parse(buf);
            }else if(type == 2){
                System.out.println("-------------------type heart beat");
                braceletdataList = parseHeartBeat.parse(buf);
            }else{
                System.out.println("type error");
                return null;
            }

        }catch(SocketException e) {
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }finally {
            socket.close();
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
    	private static ParseUdp parseBracelete = new ParseUdp();
    }
    
    public static ParseUdp getInstance() {
        return Singleton.parseBracelete;
    }
}
