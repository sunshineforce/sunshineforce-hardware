package com.sunshineforce.hardware.bootstrap;

import java.util.ArrayList;
import java.util.List;

import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.util.ByteUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseBracelete {

    public List<Braceletdata> parse(byte[] buf, String probeMac) {
        List<Braceletdata> braceletdataList = new ArrayList<>();
        //取长度
        byte[] lenByte = new byte[2];
        System.arraycopy(buf, 5, lenByte, 0, lenByte.length);
        int length = Integer.parseInt(ByteUtil.ByteToHex(lenByte), 16);//16进制转10进制
        log.info("length: "+length);

        //取温度
        byte[] temperatureByte = new byte[2];
        System.arraycopy(buf, 7, temperatureByte, 0, temperatureByte.length);
        int temperature = Integer.parseInt(ByteUtil.ByteToHex(temperatureByte), 16);
        log.info("温度："+temperature);

        //取湿度
        byte[] humidityByte = new byte[2];
        System.arraycopy(buf, 9, humidityByte, 0, humidityByte.length);
        int humidity = Integer.parseInt(ByteUtil.ByteToHex(humidityByte), 16);
        log.info("湿度："+humidity);

        //终端个数
        byte[] numByte = new byte[2];
        System.arraycopy(buf, 17, numByte, 0, numByte.length);
        int num = Integer.parseInt(ByteUtil.ByteToHex(numByte), 16);
        log.info("终端个数："+num);

        //取数据长度，length-温度-湿度-蓝牙设备mac-终端-CRC
        int dataLen = length - 2 - 2 - 6 - 2;
        log.info("数据长度："+dataLen);
        //取所有设备发送的数据
        byte[] dataByte = new byte[dataLen];
        System.arraycopy(buf, 19, dataByte, 0, dataLen);
        String dataStr = ByteUtil.ByteToHex(dataByte);
        log.info("dataStr:      " + dataStr);
        //循环取每个设备数据
        int startPos = 0;
        for (int i = 0; i < num; i++) {
            //取手环设备mac
            byte[] braceletMacByte = new byte[6];
            System.arraycopy(dataByte, startPos, braceletMacByte, 0, braceletMacByte.length);
            String braceletMac = ByteUtil.ByteToHex(braceletMacByte);
            log.info("bluetoothMac:  " + braceletMac);
            //无线设备信号强度
            byte[] signalByte = new byte[1];
            System.arraycopy(dataByte, startPos + braceletMacByte.length, signalByte, 0, signalByte.length);
            int signal = Integer.parseInt(ByteUtil.ByteToHex(signalByte), 16);
            log.info("signal  " + signal);
            //取广播数据
            byte[] broadcastValueByte = new byte[31];
            System.arraycopy(dataByte, startPos + braceletMacByte.length + signalByte.length, broadcastValueByte, 0, broadcastValueByte.length);
            String broadcastValue = ByteUtil.ByteToHex(broadcastValueByte);
            log.info("broadcastValue  " + broadcastValue);

            //b6域+b7域+b8域+mac+信号量
            startPos = startPos + 31 + 38;

            //当手环未连接app时只发送可广播数据,解析data
            Braceletdata braceletdata = getData(broadcastValueByte);
            if (braceletdata == null) {
                continue;
            }

            braceletdata.setSignalValue(signal);
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
        log.info("time："+time);

        //取crc
        byte[] crcByte = new byte[2];
        System.arraycopy(buf, timeStartPos - 3, crcByte, 0, crcByte.length);
        int crc = Integer.parseInt(ByteUtil.ByteToHex(crcByte), 16);
        log.info("crc：" + crc);

        return braceletdataList;
    }

    public Braceletdata getData(byte[] broadcastValueByte) {
        Braceletdata braceletdata = new Braceletdata();
        //取uuid
        byte[] uuidByte = new byte[6];
        System.arraycopy(broadcastValueByte, 0, uuidByte, 0, uuidByte.length);
        String uuid = ByteUtil.ByteToHex(uuidByte);
        log.info("uuid：    " + uuid);
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
        log.info("自定义数据length：   " + length);

        //取flag,自定义数据在flag之后，flag==ff
        byte[] flagByte = new byte[1];
        System.arraycopy(broadcastValueByte, 8, flagByte, 0, flagByte.length);
        String flag = ByteUtil.ByteToHex(flagByte);
        log.info("flag:    " + flag);
        if (!flag.toUpperCase().equals("FF")) {
            log.info("flag err");
            return null;
        }

        //取packageId
        byte[] packageByte = new byte[1];
        System.arraycopy(broadcastValueByte, 9, packageByte, 0, packageByte.length);
        String packageId = ByteUtil.ByteToHex(packageByte);
        log.info("package:     " + packageId);
        if (!packageId.toUpperCase().equals("B8")) {
            log.info("packageId err");
            return null;
        }

        //取自定义数据
        byte[] dataByte = new byte[length - 1];
        System.arraycopy(broadcastValueByte, 10, dataByte, 0, dataByte.length);
        log.info("自定义数据："+ByteUtil.ByteToHex(dataByte));

        //取心率
        byte[] heartRateByte = new byte[1];
        System.arraycopy(dataByte, 0, heartRateByte, 0, heartRateByte.length);
        int heartRate = Integer.parseInt(ByteUtil.ByteToHex(heartRateByte), 16);
        log.info("heartRate:    " + heartRate);
        braceletdata.setHeartRate(heartRate);

        //步数，分低字节，中字节，高字节，步数为高中低排序
        byte[] lowStepByte = new byte[1];
        System.arraycopy(dataByte, 1, lowStepByte, 0, lowStepByte.length);
        String lowStep = ByteUtil.ByteToHex(lowStepByte);
        byte[] middleStepByte = new byte[1];
        System.arraycopy(dataByte, 2, middleStepByte, 0, middleStepByte.length);
        String middleStep = ByteUtil.ByteToHex(middleStepByte);
        byte[] heightStepByte = new byte[1];
        System.arraycopy(dataByte, 3, heightStepByte, 0, heightStepByte.length);
        String heightStep = ByteUtil.ByteToHex(heightStepByte);
        String stepHex = heightStep + middleStep + lowStep;
        int step = Integer.parseInt(stepHex, 16);
        log.info("step" + step);
        braceletdata.setStep(step);

        //取位状态
        byte[] activeByte = new byte[1];
        System.arraycopy(dataByte, 4, activeByte, 0, activeByte.length);
        String activeBit = ByteUtil.byteToBit(activeByte[0]);
        log.info("bit:" + activeBit);
        braceletdata.setActive(activeBit);

        //睡眠状态
        byte[] sleepByte = new byte[1];
        System.arraycopy(dataByte, 5, sleepByte, 0, sleepByte.length);
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
        log.info("睡眠状态："+sleepStr);

        braceletdata.setSleep(sleepStr);

        //跳绳模式，01计数，02计时
        byte[] skipModelByte = new byte[1];
        System.arraycopy(dataByte, 6, skipModelByte, 0, skipModelByte.length);
        int skipModel = Integer.parseInt(ByteUtil.ByteToHex(skipModelByte), 16);
        log.info("跳绳模式： "+skipModel);
        braceletdata.setSkipModel(skipModel);

        //跳绳时间
        byte[] skipTimeByte = new byte[1];
        System.arraycopy(dataByte, 7, skipTimeByte, 0, skipTimeByte.length);
        int skipTime = Integer.parseInt(ByteUtil.ByteToHex(skipTimeByte), 16);
        log.info("跳绳时长："+skipTime);
        braceletdata.setSkipTime(skipTime);

        //跳绳数量
        byte[] skipNumLowByte = new byte[1];
        System.arraycopy(dataByte, 8, skipNumLowByte, 0, skipNumLowByte.length);
        String skipNumLow = ByteUtil.ByteToHex(skipNumLowByte);
        byte[] skipNumHeightByte = new byte[1];
        System.arraycopy(dataByte, 9, skipNumHeightByte, 0, skipNumHeightByte.length);
        String skipNumHeight = ByteUtil.ByteToHex(skipNumHeightByte);
        String skipNumStr = skipNumHeight + skipNumLow;
        int skipNum = Integer.parseInt(skipNumStr, 16);
        log.info("跳绳数量："+skipNum);
        braceletdata.setSkipNum(skipNum);

        //软件版本
        byte[] versionByte = new byte[1];
        System.arraycopy(dataByte, 10, versionByte, 0, versionByte.length);
        int version = Integer.parseInt(ByteUtil.ByteToHex(versionByte), 16);
        log.info("软件版本"+version);

        //UTC时间
        byte[] utcOneByte = new byte[1];
        System.arraycopy(dataByte, 11, utcOneByte, 0, utcOneByte.length);
        String utcOneStr = ByteUtil.ByteToHex(utcOneByte);
        byte[] utcTwoByte = new byte[1];
        System.arraycopy(dataByte, 12, utcTwoByte, 0, utcTwoByte.length);
        String utcTwoStr = ByteUtil.ByteToHex(utcTwoByte);
        byte[] utcThreeByte = new byte[1];
        System.arraycopy(dataByte, 13, utcThreeByte, 0, utcThreeByte.length);
        String utcThreeStr = ByteUtil.ByteToHex(utcThreeByte);
        byte[] utcFourByte = new byte[1];
        System.arraycopy(dataByte, 14, utcFourByte, 0, utcFourByte.length);
        String utcFourStr = ByteUtil.ByteToHex(utcFourByte);
        String utcStr = utcFourStr + utcThreeStr + utcTwoStr + utcOneStr;
        Long utc = Long.parseLong(utcStr, 16);
        log.info("utc:"+utc);
        braceletdata.setUtc(Long.parseLong(utc+""));

        //静止心率
        byte[] staticHeartRateByte = new byte[1];
        System.arraycopy(dataByte, 15, staticHeartRateByte, 0, staticHeartRateByte.length);
        int staticHeartRate = Integer.parseInt(ByteUtil.ByteToHex(staticHeartRateByte), 16);
        log.info("静止心率"+staticHeartRate);
        braceletdata.setStaticHeartRate(staticHeartRate);

        //预留
        byte[] nullByte = new byte[length - 2 - 2 - 16];
        System.arraycopy(dataByte, 16, nullByte, 0, nullByte.length);
        String nullStr = ByteUtil.ByteToHex(nullByte);
        log.info("预留：" + nullStr);

        //电池
        byte[] batteryByte = new byte[1];
        System.arraycopy(dataByte, length - 2 - 1 - 1, batteryByte, 0, batteryByte.length);
        int battery = Integer.parseInt(ByteUtil.ByteToHex(batteryByte), 16);
        braceletdata.setBattery(battery);

        //异或校验值
        byte[] orAndByte = new byte[1];
        System.arraycopy(dataByte, length - 2 - 1, orAndByte, 0, orAndByte.length);
        String orAnd = ByteUtil.ByteToHex(orAndByte);
        log.info("orAnd："+orAnd);
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
