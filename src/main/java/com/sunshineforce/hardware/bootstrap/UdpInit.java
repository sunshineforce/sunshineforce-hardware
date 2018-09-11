package com.sunshineforce.hardware.bootstrap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware; 

public class UdpInit implements InitializingBean, ServletContextAware{

	@Override
	public void setServletContext(ServletContext arg0) {
		System.out.println("222222222222222222222222");
		ParseUdp parseUdp = ParseUdp.getInstance();
		ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
		singleThreadPool.execute(new Runnable(){
			@Override
			public void run() {
				parseUdp.parse();
			}
		});
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

}
