package com.sunshineforce.hardware.service.impl;

import com.sunshineforce.hardware.bootstrap.AcConfig;
import com.sunshineforce.hardware.bootstrap.AcHeartBeat;
import com.sunshineforce.hardware.domain.ProbeInfo;
import com.sunshineforce.hardware.domain.Wifi;
import com.sunshineforce.hardware.service.IAcService;
import com.sunshineforce.hardware.util.ByteUtil;
import com.sunshineforce.hardware.util.UdpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class AcServiceImpl implements IAcService {
    @Override
    public int updateConfig(Wifi wifi) {
        log.info("-------------开始获取云AC心跳数据------------");
        //建立握手
        return AcHeartBeat.responseHeartBeatWithAcConfig(wifi);
    }

    @Override
    public int toggleWifi(Wifi wifi) {
        log.info("-------------开始获取云AC心跳数据------------");
        //建立握手,发送消息
        return AcHeartBeat.responseHeartBeatWithToggleWifi(wifi);
    }

    @Override
    public int setServer(Wifi wifi) {
        log.info("-------------开始获取云AC心跳数据------------");
        //建立握手,发送消息
        return AcHeartBeat.responseHeartBeatWithSetServer(wifi);
    }

    @Override
    public ProbeInfo requestSelect(Wifi wifi) {
        ProbeInfo probeInfo = new ProbeInfo();
        log.info("-------------开始获取云AC心跳数据------------");
        //建立握手,发送消息
        byte[] probeInfoByte = AcHeartBeat.requestSelect(wifi);
        if(probeInfoByte == null || probeInfoByte.length <= 0){
            probeInfo.setProbeMac("--");
            probeInfo.setWifiSsid("--");
            probeInfo.setWifiPwd("--");
            probeInfo.setWifiStatus("--");
            probeInfo.setServerIp("--");
            probeInfo.setServerPort(0);
            probeInfo.setVersion("--");
            return probeInfo;
        }
        byte[] responseTypeByte = new byte[1];
        System.arraycopy(probeInfoByte, 2, responseTypeByte, 0, responseTypeByte.length);
        if(!"bd".equals(ByteUtil.ByteToHex(responseTypeByte))){
            probeInfo.setProbeMac("--");
            probeInfo.setWifiSsid("--");
            probeInfo.setWifiPwd("--");
            probeInfo.setWifiStatus("--");
            probeInfo.setServerIp("--");
            probeInfo.setServerPort(0);
            probeInfo.setVersion("--");
            return probeInfo;
        }

        byte[] probeMacByte = new byte[6];
        System.arraycopy(probeInfoByte, 5, probeMacByte, 0, probeMacByte.length);
        probeInfo.setProbeMac(ByteUtil.ByteToHex(probeMacByte));

        byte[] wifiSsidLengthByte = new byte[1];
        System.arraycopy(probeInfoByte, 11, wifiSsidLengthByte, 0, wifiSsidLengthByte.length);
        Integer wifiSsidLength = Integer.parseInt(ByteUtil.ByteToHex(wifiSsidLengthByte), 16);
        log.info("wifiSsidLength-----"+wifiSsidLength);

        byte[] wifiSsidByte = new byte[wifiSsidLength];
        System.arraycopy(probeInfoByte, 12, wifiSsidByte, 0, wifiSsidByte.length);
        String wifiSsid = ByteUtil.ByteToHex(wifiSsidByte);
        probeInfo.setWifiSsid(wifiSsid);

        byte[] wifiPwdLengthByte = new byte[1];
        System.arraycopy(probeInfoByte, 12+wifiSsidLength, wifiPwdLengthByte, 0, wifiPwdLengthByte.length);
        Integer wifiPwdLength = Integer.parseInt(ByteUtil.ByteToHex(wifiPwdLengthByte), 16);
        log.info("wifiPwdLength-----"+wifiPwdLength);

        byte[] wifiPwdByte = new byte[wifiPwdLength];
        System.arraycopy(probeInfoByte, 12+wifiSsidLength+1, wifiPwdByte, 0, wifiPwdByte.length);
        String wifiPwd = ByteUtil.ByteToHex(wifiPwdByte);
        probeInfo.setWifiPwd(wifiPwd);
        log.info("wifipwd======"+wifiPwd);

        byte[] wifiStatusByte = new byte[1];
        System.arraycopy(probeInfoByte, 12+wifiPwdLength+wifiSsidLength+1, wifiStatusByte, 0, wifiStatusByte.length);
        int wifiStatus = Integer.parseInt(ByteUtil.ByteToHex(wifiStatusByte), 16);
        if(wifiStatus == 0){
            probeInfo.setWifiStatus("打开");
        }else{
            probeInfo.setWifiStatus("关闭");
        }
        log.info("status-----"+ByteUtil.ByteToHex(wifiStatusByte));

        byte[] serverIpByte = new byte[4];
        System.arraycopy(probeInfoByte, 12+wifiPwdLength+wifiSsidLength+1+1, serverIpByte, 0, serverIpByte.length);
        String serverIp = UdpUtil.hexToip(ByteUtil.ByteToHex(serverIpByte));
        probeInfo.setServerIp(serverIp);
        log.info("serverIp-----"+serverIp);

        byte[] serverPortByte = new byte[2];
        System.arraycopy(probeInfoByte, 12+wifiPwdLength+wifiSsidLength+1+1+4, serverPortByte, 0, serverPortByte.length);
        int serverPort = Integer.parseInt(ByteUtil.ByteToHex(serverPortByte), 16);
        probeInfo.setServerPort(serverPort);

        byte[] versionLengthByte = new byte[1];
        System.arraycopy(probeInfoByte, 12+wifiPwdLength+wifiSsidLength+1+1+4+2+1, versionLengthByte, 0, versionLengthByte.length);
        Integer versionLength = Integer.parseInt(ByteUtil.ByteToHex(versionLengthByte), 16);
        log.info("versionLength-----"+versionLength);

        byte[] versionByte = new byte[versionLength];
        System.arraycopy(probeInfoByte, 12+wifiPwdLength+wifiSsidLength+1+1+4+2+1+1, versionByte, 0, versionByte.length);
        String version = ByteUtil.ByteToHex(versionByte);
        probeInfo.setVersion(version);
        return probeInfo;
    }

    @Override
    public int restart(Wifi wifi) {
        ExecutorService acSingleThreadPool = Executors.newSingleThreadExecutor();
        acSingleThreadPool.execute(new Runnable(){
            @Override
            public void run() {
                log.info("-------------开始获取云AC心跳数据------------");
                //建立握手,发送消息
                AcHeartBeat.restart(wifi);
            }
        });
        return 0;
    }

    public static void main(String args[]){
        Wifi wifi = new Wifi();
        wifi.setStatus(0);
        wifi.setSsid("lj");
        wifi.setProbeMac("caaa16140773");
        wifi.setPassword("95091810");
        wifi.setPort(3333);
        wifi.setIp("192.168.0.100");
        AcServiceImpl acService = new AcServiceImpl();
        //acService.toggleWifi(wifi);
        //acService.updateConfig(wifi);
        acService.setServer(wifi);
        //acService.requestSelect(wifi);
    }
}
