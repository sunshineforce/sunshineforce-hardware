package com.sunshineforce.hardware.bootstrap;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.service.IBraceletdataService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

public class UdpInit implements InitializingBean, ServletContextAware{

    @Resource
    private IBraceletdataService iBraceletdataService;

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public void setServletContext(ServletContext servletContext) {
        final ParseUdp parseUdp = ParseUdp.getInstance();
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        singleThreadPool.execute(new Runnable(){
            @Override
            public void run() {
                System.out.println("222222222222222222222222");
                while(true){
                    List<Braceletdata> braceletdataList = parseUdp.parse();
                    iBraceletdataService.insertList(braceletdataList);
                }
            }
        });
	}
}
