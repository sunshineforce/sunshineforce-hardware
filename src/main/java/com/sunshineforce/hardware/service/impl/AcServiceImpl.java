package com.sunshineforce.hardware.service.impl;

import com.sunshineforce.hardware.bootstrap.AcConfig;
import com.sunshineforce.hardware.bootstrap.AcHeartBeat;
import com.sunshineforce.hardware.domain.Wifi;
import com.sunshineforce.hardware.service.IAcService;
import com.sunshineforce.hardware.util.ByteUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class AcServiceImpl implements IAcService {
    @Override
    public int updateConfig(Wifi wifi) {
        ExecutorService acSingleThreadPool = Executors.newSingleThreadExecutor();
        acSingleThreadPool.execute(new Runnable(){
            @Override
            public void run() {
                log.info("-------------开始获取云AC心跳数据------------");
                //建立握手
                AcHeartBeat.responseHeartBeatWithAcConfig(wifi);
            }
        });
        return 0;
    }

    @Override
    public int toggleWifi(Wifi wifi) {
        ExecutorService acSingleThreadPool = Executors.newSingleThreadExecutor();
        acSingleThreadPool.execute(new Runnable(){
            @Override
            public void run() {
                log.info("-------------开始获取云AC心跳数据------------");
                //建立握手,发送消息
                AcHeartBeat.responseHeartBeatWithToggleWifi(wifi);
            }
        });
        return 0;
    }

    @Override
    public int setServer(Wifi wifi) {
        ExecutorService acSingleThreadPool = Executors.newSingleThreadExecutor();
        acSingleThreadPool.execute(new Runnable(){
            @Override
            public void run() {
                log.info("-------------开始获取云AC心跳数据------------");
                //建立握手,发送消息
                AcHeartBeat.responseHeartBeatWithSetServer(wifi);
            }
        });
        return 0;
    }

    @Override
    public int requestSelect(Wifi wifi) {
        ExecutorService acSingleThreadPool = Executors.newSingleThreadExecutor();
        acSingleThreadPool.execute(new Runnable(){
            @Override
            public void run() {
                log.info("-------------开始获取云AC心跳数据------------");
                //建立握手,发送消息
                AcHeartBeat.requestSelect(wifi);
            }
        });
        return 0;
    }

    public static void main(String args[]){
        Wifi wifi = new Wifi();
        wifi.setStatus(0);
        wifi.setSsid("lj2");
        wifi.setProbeMac("c914eff6c830");
        wifi.setPassword("95091810");
        wifi.setPort(3333);
        wifi.setIp("192.168.0.100");
        AcServiceImpl acService = new AcServiceImpl();
        acService.toggleWifi(wifi);
        //acService.updateConfig(wifi);
        //acService.setServer(wifi);
        //acService.requestSelect(wifi);
    }
}
