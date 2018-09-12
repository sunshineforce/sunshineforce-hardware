package com.sunshineforce.hardware.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sunshineforce.hardware.domain.Bluetooth;
import com.sunshineforce.hardware.service.IBluetoothService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

public class UdpInit implements InitializingBean, ServletContextAware{

    @Autowired
    private IBluetoothService iBluetoothService;

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public void setServletContext(ServletContext servletContext) {
        System.out.println("222222222222222222222222");
        final ParseUdp parseUdp = ParseUdp.getInstance();
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        singleThreadPool.execute(new Runnable(){
            @Override
            public void run() {
                List<Bluetooth> bluetoothList = parseUdp.parse();
                iBluetoothService.insertList(bluetoothList);
            }
        });
	}
}
