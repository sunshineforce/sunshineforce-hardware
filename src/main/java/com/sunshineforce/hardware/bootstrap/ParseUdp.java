package com.sunshineforce.hardware.bootstrap;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.sunshineforce.hardware.util.ByteUtil;

public class ParseUdp {
    private static final String HEADER = "ADBA";

    public String parse(){
        DatagramSocket socket = null;
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
                return "header false";
            }

            byte[] typeByte = new byte[1];
            System.arraycopy(buf, 4, typeByte, 0, typeByte.length); //取消息类型
            int type = Integer.parseInt(ByteUtil.ByteToHex(typeByte), 16);
            if(type != 1){
                return "type error";
            }

            //取长度
            byte[] lenByte = new byte[2];
            System.arraycopy(buf, 5, lenByte, 0, lenByte.length);
            int length = Integer.parseInt(ByteUtil.ByteToHex(lenByte), 16);//16进制转10进制
            System.out.println(length);

            //取温度
            byte[] temperatureByte = new byte[2];
            System.arraycopy(buf, 7, temperatureByte, 0, temperatureByte.length);
            int temperature = Integer.parseInt(ByteUtil.ByteToHex(temperatureByte), 16);
            System.out.println(temperature);

            //取湿度
            byte[] humidityByte = new byte[2];
            System.arraycopy(buf, 9, humidityByte, 0, humidityByte.length);
            int humidity = Integer.parseInt(ByteUtil.ByteToHex(humidityByte), 16);
            System.out.println(humidity);

            //取蓝牙设备mac
            byte[] macByte = new byte[6];
            System.arraycopy(buf, 11, macByte, 0, macByte.length);
            String mac = ByteUtil.ByteToHex(macByte);
            System.out.println(mac);

            //终端个数
            byte[] numByte = new byte[2];
            System.arraycopy(buf, 17, numByte, 0, numByte.length);
            int num = Integer.parseInt(ByteUtil.ByteToHex(numByte), 16);
            System.out.println(num);

            //取数据长度，12-温度-湿度-蓝牙设备mac-终端-CRC
            int dataLen = length - 2 - 2 - 6 - 2;
            System.out.println(dataLen);
            //取所有设备发送的数据
            byte[] dataByte = new byte[dataLen];
            System.arraycopy(buf, 19, dataByte, 0, dataLen);
            String dataStr = ByteUtil.ByteToHex(dataByte);
            System.out.println("dataStr:      "+dataStr);
            //循环取每个设备数据
            for(int i = 0; i < num; i++){
                int startPos = i * 38;
                //取无线设备mac
                byte[] wifiMacByte = new byte[6];
                System.arraycopy(dataByte, startPos, wifiMacByte, 0, wifiMacByte.length);
                String wifiMac = ByteUtil.ByteToHex(wifiMacByte);
                System.out.println("wifiMac:  "+wifiMac);
                //无线设备信号强度
                byte[] signalByte = new byte[1];
                System.arraycopy(dataByte, startPos + wifiMacByte.length, signalByte, 0, signalByte.length);
                int signal = Integer.parseInt(ByteUtil.ByteToHex(signalByte),16);
                System.out.println("signal  "+signal);
                //取广播数据
                byte[] broadcastValueByte = new byte[31];
                System.arraycopy(dataByte, startPos + wifiMacByte.length + signalByte.length, broadcastValueByte, 0, broadcastValueByte.length);
                String broadcastValue = ByteUtil.ByteToHex(broadcastValueByte);
                System.out.println("broadcastValue  "+broadcastValue);
                System.out.println("broadcastValue  "+broadcastValue.length());

                //当手环未连接app时只发送可广播数据,解析data
                getData(broadcastValueByte);


            }

            //取time
            byte[] timeByte = new byte[19];
            int timeStartPos = 2 + 2 + 1 + 2 + length + 2;
            System.arraycopy(buf, timeStartPos, timeByte, 0, timeByte.length);
            String time = new String(timeByte);
            System.out.println(time);

            //取crc
            byte[] crcByte = new byte[2];
            System.arraycopy(buf, timeStartPos - 3, crcByte, 0, crcByte.length);
            int crc = Integer.parseInt(ByteUtil.ByteToHex(crcByte),16);
            System.out.println(crc);

        }catch(SocketException e) {
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }finally {
            socket.close();
        }
        return "";
    }

    //check header
    public boolean checkHeqader(byte[] header){
        System.out.println(ByteUtil.ByteToHex(header));
        return HEADER.equals(ByteUtil.ByteToHex(header).toUpperCase());
    }

    public String getData(byte[] broadcastValueByte){
        System.out.println("broadcastValue");
        //取uuid
        byte[] uuidByte = new byte[6];
        System.arraycopy(broadcastValueByte, 0, uuidByte, 0, uuidByte.length);
        String uuid = ByteUtil.ByteToHex(uuidByte);
        System.out.println("uuid：    "+uuid);

//        //取设备名字
//        byte[] nameByte = new byte[2];
//        System.arraycopy(broadcastValueByte, 6, nameByte, 0, nameByte.length);
//        String name = Util.ByteToHex(nameByte);
//        System.out.println("name:    "+name);

        //取自定义数据length
        byte[] lengthByte = new byte[1];
        System.arraycopy(broadcastValueByte, 7, lengthByte, 0, lengthByte.length);
        int length = Integer.parseInt(ByteUtil.ByteToHex(lengthByte), 16);
        System.out.println("length：   "+length);

        //取flag,自定义数据在flag之后，flag==ff
        byte[] flagByte = new byte[1];
        System.arraycopy(broadcastValueByte, 8, flagByte, 0, flagByte.length);
        String flag = ByteUtil.ByteToHex(flagByte);
        System.out.println("flag:    "+flag);

        //取packageId
        byte[] packageByte = new byte[1];
        System.arraycopy(broadcastValueByte, 9, packageByte, 0, packageByte.length);
        String packageId = ByteUtil.ByteToHex(packageByte);
        System.out.println("package:     "+packageId);
        if(!packageId.toUpperCase().equals("B8")){
            return "packageId err";
        }

        //取心率
        byte[] heartRateByte = new byte[1];
        System.arraycopy(broadcastValueByte, 10, heartRateByte, 0, heartRateByte.length);
        int heartRate = Integer.parseInt(ByteUtil.ByteToHex(heartRateByte), 16);
        System.out.println("heartRate:    "+heartRate);

        //步数，分低字节，中字节，高字节，步数为高中低排序
        byte[] lowStepByte = new byte[1];
        System.arraycopy(broadcastValueByte, 11, lowStepByte, 0, lowStepByte.length);
        String lowStep = ByteUtil.ByteToHex(lowStepByte);
        byte[] middleStepByte = new byte[1];
        System.arraycopy(broadcastValueByte, 12, middleStepByte, 0, middleStepByte.length);
        String middleStep = ByteUtil.ByteToHex(middleStepByte);
        byte[] heightStepByte = new byte[1];
        System.arraycopy(broadcastValueByte, 13, heightStepByte, 0, heightStepByte.length);
        String heightStep = ByteUtil.ByteToHex(heightStepByte);
        String stepHex = heightStep + middleStep + lowStep;
        System.out.println("stepHex:    "+stepHex);

        int step = Integer.parseInt(stepHex,16);
        System.out.println("step"+step);

        //取活动状态
        byte[] activeByte = new byte[1];
        System.arraycopy(broadcastValueByte, 14, activeByte, 0, activeByte.length);
        String active = ByteUtil.ByteToHex(activeByte);
        System.out.println(active);

        //睡眠状态
        byte[] sleepByte = new byte[1];
        System.arraycopy(broadcastValueByte, 15, sleepByte, 0, sleepByte.length);
        String sleep = ByteUtil.ByteToHex(sleepByte);

        String sleepStr = "";
        if(sleep.equals("00")){
            sleepStr = "清醒状态";
        }else if(sleep.equals("01")){
            sleepStr = "浅度睡眠状态";
        }else if(sleep.equals("02")){
            sleepStr = "深度睡眠状态";
        }else if(sleep.toUpperCase().equals("FF")){
            sleepStr = "未监测";
        }
        System.out.println(sleepStr);

        //舒张压
        byte[] diastolicPressureByte = new byte[1];
        System.arraycopy(broadcastValueByte, 16, diastolicPressureByte, 0, diastolicPressureByte.length);
        int diastolicPressure = Integer.parseInt(ByteUtil.ByteToHex(diastolicPressureByte),16);
        System.out.println(diastolicPressure);

        //收缩压
        byte[] systolicPressureByte = new byte[1];
        System.arraycopy(broadcastValueByte, 17, systolicPressureByte, 0, systolicPressureByte.length);
        int systolicPressure = Integer.parseInt(ByteUtil.ByteToHex(systolicPressureByte),16);
        System.out.println(systolicPressure);

        //血氧
        byte[] bloodOxygenByte = new byte[1];
        System.arraycopy(broadcastValueByte, 18, bloodOxygenByte, 0, bloodOxygenByte.length);
        int bloodOxygen = Integer.parseInt(ByteUtil.ByteToHex(bloodOxygenByte),16);
        System.out.println(bloodOxygen);

        //HRV
        byte[] hrvByte = new byte[1];
        System.arraycopy(broadcastValueByte, 19, hrvByte, 0, hrvByte.length);
        int hrv = Integer.parseInt(ByteUtil.ByteToHex(hrvByte),16);
        System.out.println(hrv);

        //软件版本
        byte[] versionByte = new byte[1];
        System.arraycopy(broadcastValueByte, 20, versionByte, 0, versionByte.length);
        int version = Integer.parseInt(ByteUtil.ByteToHex(versionByte),16);
        System.out.println(version);

        //UTC时间
        byte[] utcByte = new byte[4];
        System.arraycopy(broadcastValueByte, 20, utcByte, 0, utcByte.length);
        long utc = Long.parseLong(ByteUtil.ByteToHex(utcByte),16);
        System.out.println(utc);

        //静止心率
        byte[] staticHeartRateByte = new byte[1];
        System.arraycopy(broadcastValueByte, 24, staticHeartRateByte, 0, staticHeartRateByte.length);
        int staticHeartRate = Integer.parseInt(ByteUtil.ByteToHex(staticHeartRateByte),16);
        System.out.println(staticHeartRate);

        //预留
        byte[] nullByte = new byte[1];
        System.arraycopy(broadcastValueByte, 25, nullByte, 0, nullByte.length);
        String nullStr = ByteUtil.ByteToHex(nullByte);
        System.out.println(nullStr);

        //电池
        byte[] batteryByte = new byte[1];
        System.arraycopy(broadcastValueByte, length - 2, batteryByte, 0, batteryByte.length);
        String battery = ByteUtil.ByteToHex(batteryByte);
        System.out.println(battery);

        //异或校验值
        byte[] orAndByte = new byte[1];
        System.arraycopy(broadcastValueByte, length - 1, orAndByte, 0, orAndByte.length);
        String orAnd = ByteUtil.ByteToHex(orAndByte);
        System.out.println(orAnd);
        return "";
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
