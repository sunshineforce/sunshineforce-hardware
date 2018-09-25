package com.sunshineforce.hardware.bootstrap;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.service.IBraceletdataService;
import com.sunshineforce.hardware.service.IProbeService;
import com.sunshineforce.hardware.util.ByteUtil;
import com.sunshineforce.hardware.util.UdpUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

public class UdpInit implements InitializingBean, ServletContextAware{

    @Resource
    private IBraceletdataService iBraceletdataService;

    @Resource
    private IProbeService iProbeService;


    private static final String HEADER = "ADBA";

    private ParseBracelete parseBracelete = ParseBracelete.getInstance();

    private ParseHeartBeat parseHeartBeat = ParseHeartBeat.getInstance();

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public void setServletContext(ServletContext servletContext) {
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        singleThreadPool.execute(new Runnable(){
            @Override
            public void run() {
                System.out.println("222222222222222222222222");
                while(true){
                    List<Braceletdata> braceletdataList = new ArrayList<>();
                    byte[] buf = UdpUtil.getUdpResponse(3333);
                    byte[] header = new byte[2];
                    System.arraycopy(buf, 0, header, 0, header.length); //取前两个字节
                    if(!checkHeqader(header)){
                        System.out.println("header false");
                    }else{
                        //取探针设备mac
                        byte[] probeMacByte = new byte[6];
                        System.arraycopy(buf, 11, probeMacByte, 0, probeMacByte.length);
                        String probeMac = ByteUtil.ByteToHex(probeMacByte);
                        System.out.println(probeMac);
                        int count = iProbeService.getProbeByMac(probeMac);
                        if(count <= 0){
                            System.out.println("-------------------the probe is not authorize");
                        }else{
                            byte[] typeByte = new byte[1];
                            System.arraycopy(buf, 4, typeByte, 0, typeByte.length); //取消息类型
                            int type = Integer.parseInt(ByteUtil.ByteToHex(typeByte), 16);
                            if(type == 1){
                                braceletdataList = parseBracelete.parse(buf, probeMac);
                                iBraceletdataService.insertList(braceletdataList);
                            }else if(type == 2){
                                System.out.println("-------------------type heart beat");
                            }else{
                                System.out.println("type error");
                            }
                        }
                    }
//                    MessageUpload messageUpload = new MessageUpload();
//                    try{
//                        messageUpload.getMessage("caaa16140773" ,183);
//                    }catch(UnknownHostException e){
//
//                    }
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
