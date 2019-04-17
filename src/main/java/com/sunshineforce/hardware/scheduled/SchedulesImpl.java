package com.sunshineforce.hardware.scheduled;

import com.google.gson.Gson;
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

    @Scheduled(cron = "0 0/5 * * * ?")
    //@Scheduled(cron = "0 06 21 * * ?")
    public void exportData(){
        Gson gson = new Gson();
        BraceletdataRequest exportData = new BraceletdataRequest();
        exportData.setBeginTime(System.currentTimeMillis() - 60 * 1000 * 5);
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
        log.info(gson.toJson(map));
        log.info("---------"+gson.toJson(map).getBytes().length);
        HttpUtil.sendPost(map, "http://www.sunshineforce.com:7014/data/data/getData");
        //HttpUtil.sendPost(map, "http://localhost:8888/data/getData");
    }
}
