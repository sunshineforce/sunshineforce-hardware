package com.sunshineforce.hardware.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import com.sunshineforce.hardware.dao.mapper.BraceletdataMapper;
import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.service.IProbeService;
import com.sunshineforce.hardware.util.ByteUtil;
import com.sunshineforce.hardware.util.UdpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Slf4j
public class UdpInit implements InitializingBean, ServletContextAware{

    @Autowired
    private BraceletdataMapper braceletdataMapper;

    @Autowired
    private IProbeService iProbeService;


    private static final String HEADER = "ADBA";

    private ParseBracelete parseBracelete = ParseBracelete.getInstance();

    private static AtomicInteger count = new AtomicInteger();// 总数 原子操作

	@Override
	public void afterPropertiesSet() {

	}

	@Override
	public void setServletContext(ServletContext servletContext) {
        BlockingQueue<byte[]> queue = new LinkedBlockingDeque<>(1000);
        ExecutorService udpSingleThreadPool = Executors.newSingleThreadExecutor();
        udpSingleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                log.info("-------------生产者启动------------");
                while (true) {
                    byte[] buf = UdpUtil.getUdpResponse(3333);
                    try {
                        queue.put(buf);
                    } catch (InterruptedException e) {
                        log.error(e.getLocalizedMessage());
                    }
                }
            }
        });

        ExecutorService udpfixedThreadPool = Executors.newFixedThreadPool(5);
        udpfixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("-------------消费者启动------------");
                    while(true){
                        byte[] buf = queue.take();
                        log.info(ByteUtil.ByteToHex(buf));
                        List<Braceletdata> braceletdataList = new ArrayList<>();
                        byte[] header = new byte[2];
                        System.arraycopy(buf, 0, header, 0, header.length); //取前两个字节
                        if(!checkHeqader(header)){
                            log.info("header false");
                        }else{
                            //取探针设备mac
                            byte[] probeMacByte = new byte[6];
                            System.arraycopy(buf, 11, probeMacByte, 0, probeMacByte.length);
                            String probeMac = ByteUtil.ByteToHex(probeMacByte);
                            int count = iProbeService.getProbeByMac(probeMac);
                            if(count <= 0){
                                log.info("-------------------the probe is not authorize,probeMac is : " + probeMac);
                            }else{
                                byte[] typeByte = new byte[1];
                                System.arraycopy(buf, 4, typeByte, 0, typeByte.length); //取消息类型
                                int type = Integer.parseInt(ByteUtil.ByteToHex(typeByte), 16);
                                if(type == 1){
                                    braceletdataList = parseBracelete.parse(buf, probeMac);
                                    braceletdataList.stream().forEach(braceletdata -> braceletdataMapper.replaceInsert(braceletdata));
                                }else if(type == 2){
                                    log.info("-------------------type heart beat");
                                }else{
                                    log.info("type error");
                                }
                            }
                        }
                    }
                }catch(Exception e) {
                    log.error(e.getLocalizedMessage());
                }
            }
        });
    }

    //check header
    private boolean checkHeqader(byte[] header){
        System.out.println(ByteUtil.ByteToHex(header));
        return HEADER.equals(ByteUtil.ByteToHex(header).toUpperCase());
    }
}
