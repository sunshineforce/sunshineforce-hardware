package com.sunshineforce.hardware.bootstrap;

import java.util.ArrayList;
import java.util.List;

import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.util.ByteUtil;

public class ParseBracelete {

    public List<Braceletdata> parse(byte[] buf) {
        List<Braceletdata> braceletdataList = new ArrayList<>();
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

        //取探针设备mac
        byte[] probeMacByte = new byte[6];
        System.arraycopy(buf, 11, probeMacByte, 0, probeMacByte.length);
        String probeMac = ByteUtil.ByteToHex(probeMacByte);
        System.out.println(probeMac);

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
        System.out.println("dataStr:      " + dataStr);
        //循环取每个设备数据
        for (int i = 0; i < num; i++) {
            int startPos = i * 38;
            //取手环设备mac
            byte[] braceletMacByte = new byte[6];
            System.arraycopy(dataByte, startPos, braceletMacByte, 0, braceletMacByte.length);
            String braceletMac = ByteUtil.ByteToHex(braceletMacByte);
            System.out.println("bluetoothMac:  " + braceletMac);
            //无线设备信号强度
            byte[] signalByte = new byte[1];
            System.arraycopy(dataByte, startPos + braceletMacByte.length, signalByte, 0, signalByte.length);
            int signal = Integer.parseInt(ByteUtil.ByteToHex(signalByte), 16);
            System.out.println("signal  " + signal);
            //取广播数据
            byte[] broadcastValueByte = new byte[31];
            System.arraycopy(dataByte, startPos + braceletMacByte.length + signalByte.length, broadcastValueByte, 0, broadcastValueByte.length);
            String broadcastValue = ByteUtil.ByteToHex(broadcastValueByte);
            System.out.println("broadcastValue  " + broadcastValue);
            System.out.println("broadcastValue  " + broadcastValue.length());

            //当手环未连接app时只发送可广播数据,解析data
            Braceletdata braceletdata = getData(broadcastValueByte);
            if (braceletdata == null) {
                continue;
            }
            braceletdata.setBraceletMac(braceletMac);
            braceletdata.setProbeMac(probeMac);
            braceletdata.setAddTime(System.currentTimeMillis());
            braceletdataList.add(braceletdata);
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
        int crc = Integer.parseInt(ByteUtil.ByteToHex(crcByte), 16);
        System.out.println(crc);

        return braceletdataList;
    }

    public Braceletdata getData(byte[] broadcastValueByte) {
        Braceletdata braceletdata = new Braceletdata();
        System.out.println("broadcastValue");
        //取uuid
        byte[] uuidByte = new byte[6];
        System.arraycopy(broadcastValueByte, 0, uuidByte, 0, uuidByte.length);
        String uuid = ByteUtil.ByteToHex(uuidByte);
        System.out.println("uuid：    " + uuid);
        braceletdata.setUuid(uuid);

//        //取设备名字
//        byte[] nameByte = new byte[2];
//        System.arraycopy(broadcastValueByte, 6, nameByte, 0, nameByte.length);
//        String name = Util.ByteToHex(nameByte);
//        System.out.println("name:    "+name);

        //取自定义数据length
        byte[] lengthByte = new byte[1];
        System.arraycopy(broadcastValueByte, 7, lengthByte, 0, lengthByte.length);
        int length = Integer.parseInt(ByteUtil.ByteToHex(lengthByte), 16);
        System.out.println("length：   " + length);
        System.out.println("length：   " + ByteUtil.ByteToHex(lengthByte));

        //取flag,自定义数据在flag之后，flag==ff
        byte[] flagByte = new byte[1];
        System.arraycopy(broadcastValueByte, 8, flagByte, 0, flagByte.length);
        String flag = ByteUtil.ByteToHex(flagByte);
        System.out.println("flag:    " + flag);

        //取packageId
        byte[] packageByte = new byte[1];
        System.arraycopy(broadcastValueByte, 9, packageByte, 0, packageByte.length);
        String packageId = ByteUtil.ByteToHex(packageByte);
        System.out.println("package:     " + packageId);
        if (!packageId.toUpperCase().equals("B8")) {
            System.out.println("packageId err");
            return null;
        }

        //取心率
        byte[] heartRateByte = new byte[1];
        System.arraycopy(broadcastValueByte, 10, heartRateByte, 0, heartRateByte.length);
        int heartRate = Integer.parseInt(ByteUtil.ByteToHex(heartRateByte), 16);
        System.out.println("heartRate:    " + heartRate);
        System.out.println("heartRate:    " + ByteUtil.ByteToHex(heartRateByte));
        braceletdata.setHeartRate(heartRate);

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
        System.out.println("stepHex:    " + stepHex);

        int step = Integer.parseInt(stepHex, 16);
        System.out.println("step" + step);

        braceletdata.setStep(step);

        //取活动状态
        byte[] activeByte = new byte[1];
        System.arraycopy(broadcastValueByte, 14, activeByte, 0, activeByte.length);
        String active = ByteUtil.ByteToHex(activeByte);
        System.out.println(active);

        braceletdata.setActive(active);

        //睡眠状态
        byte[] sleepByte = new byte[1];
        System.arraycopy(broadcastValueByte, 15, sleepByte, 0, sleepByte.length);
        String sleep = ByteUtil.ByteToHex(sleepByte);

        String sleepStr = "";
        if (sleep.equals("00")) {
            sleepStr = "清醒状态";
        } else if (sleep.equals("01")) {
            sleepStr = "浅度睡眠状态";
        } else if (sleep.equals("02")) {
            sleepStr = "深度睡眠状态";
        } else if (sleep.toUpperCase().equals("FF")) {
            sleepStr = "未监测";
        }
        System.out.println(sleepStr);

        braceletdata.setSleep(sleepStr);

        //舒张压
        byte[] diastolicPressureByte = new byte[1];
        System.arraycopy(broadcastValueByte, 16, diastolicPressureByte, 0, diastolicPressureByte.length);
        int diastolicPressure = Integer.parseInt(ByteUtil.ByteToHex(diastolicPressureByte), 16);
        System.out.println(diastolicPressure);
        braceletdata.setDiastolicPressure(diastolicPressure);

        //收缩压
        byte[] systolicPressureByte = new byte[1];
        System.arraycopy(broadcastValueByte, 17, systolicPressureByte, 0, systolicPressureByte.length);
        int systolicPressure = Integer.parseInt(ByteUtil.ByteToHex(systolicPressureByte), 16);
        System.out.println(systolicPressure);
        braceletdata.setSystolicPressure(systolicPressure);

        //血氧
        byte[] bloodOxygenByte = new byte[1];
        System.arraycopy(broadcastValueByte, 18, bloodOxygenByte, 0, bloodOxygenByte.length);
        int bloodOxygen = Integer.parseInt(ByteUtil.ByteToHex(bloodOxygenByte), 16);
        System.out.println(bloodOxygen);
        braceletdata.setBloodOxygen(bloodOxygen);

        //HRV
        byte[] hrvByte = new byte[1];
        System.arraycopy(broadcastValueByte, 19, hrvByte, 0, hrvByte.length);
        int hrv = Integer.parseInt(ByteUtil.ByteToHex(hrvByte), 16);
        System.out.println(hrv);
        braceletdata.setHrv(hrv);

        //软件版本
        byte[] versionByte = new byte[1];
        System.arraycopy(broadcastValueByte, 20, versionByte, 0, versionByte.length);
        int version = Integer.parseInt(ByteUtil.ByteToHex(versionByte), 16);
        System.out.println(version);

        //UTC时间
        byte[] utcByte = new byte[4];
        System.arraycopy(broadcastValueByte, 21, utcByte, 0, utcByte.length);
        long utc = Long.parseLong(ByteUtil.ByteToHex(utcByte), 16);
        System.out.println(utc);
        System.out.println("utc:"+ByteUtil.ByteToHex(utcByte));
        braceletdata.setUtc(utc);

        //静止心率
        byte[] staticHeartRateByte = new byte[1];
        System.arraycopy(broadcastValueByte, 25, staticHeartRateByte, 0, staticHeartRateByte.length);
        int staticHeartRate = Integer.parseInt(ByteUtil.ByteToHex(staticHeartRateByte), 16);
        System.out.println(staticHeartRate);
        braceletdata.setStaticHeartRate(staticHeartRate);

        //预留
        byte[] nullByte = new byte[length + 6 - 25];
        System.arraycopy(broadcastValueByte, 26, nullByte, 0, nullByte.length);
        String nullStr = ByteUtil.ByteToHex(nullByte);
        System.out.println(nullStr);

        //电池
        byte[] batteryByte = new byte[1];
        System.arraycopy(broadcastValueByte, length + 9 - 2, batteryByte, 0, batteryByte.length);
        int battery = Integer.parseInt(ByteUtil.ByteToHex(batteryByte), 16);
        braceletdata.setBattery(battery);
        System.out.println("battery："+battery);
        System.out.println("battery："+ByteUtil.ByteToHex(batteryByte));

        //异或校验值
        byte[] orAndByte = new byte[1];
        System.arraycopy(broadcastValueByte, length + 9 - 1, orAndByte, 0, orAndByte.length);
        String orAnd = ByteUtil.ByteToHex(orAndByte);
        System.out.println("orAnd："+orAnd);
        return braceletdata;
    }

    private ParseBracelete() {

    }

    private static class Singleton {
        private static ParseBracelete parseBracelete = new ParseBracelete();
    }

    public static ParseBracelete getInstance() {
        return Singleton.parseBracelete;
    }
}
