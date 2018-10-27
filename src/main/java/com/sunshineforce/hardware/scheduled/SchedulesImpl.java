package com.sunshineforce.hardware.scheduled;

import com.google.gson.Gson;
import com.sunshineforce.hardware.dao.mapper.BraceletdataMapper;
import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.domain.ExportData;
import com.sunshineforce.hardware.domain.request.BraceletdataRequest;
import com.sunshineforce.hardware.service.IBraceletdataService;
import com.sunshineforce.hardware.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class SchedulesImpl {

    @Autowired
    private IBraceletdataService iBraceletdataService;

    @Scheduled(cron = "0/30 * * * * ?")
    public void exportData(){
        Gson gson = new Gson();
        BraceletdataRequest exportData = new BraceletdataRequest();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);//昨天0点0分0秒
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startTime = calendar.getTime().getTime();
        log.info("----------"+startTime);
        exportData.setBeginTime(startTime);
        exportData.setEndTime(System.currentTimeMillis());
        List<Braceletdata> braceletdataList = iBraceletdataService.getBraceletdatasList(exportData);
        List<ExportData> exportDataList = new ArrayList<>();
        for(Braceletdata braceletdata : braceletdataList){
            ExportData exportData1 = new ExportData();
            BeanUtils.copyProperties(braceletdata, exportData1);
            exportDataList.add(exportData1);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", gson.toJson(exportDataList));
        System.out.println(gson.toJson(map));
        HttpUtil.sendPost(map, "http://localhost:8888/data/getData");
    }
}
