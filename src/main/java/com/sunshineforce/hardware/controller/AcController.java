package com.sunshineforce.hardware.controller;

import com.sunshineforce.hardware.base.utils.ResponseData;
import com.sunshineforce.hardware.domain.ProbeInfo;
import com.sunshineforce.hardware.domain.Wifi;
import com.sunshineforce.hardware.domain.response.ProbeInfoResponse;
import com.sunshineforce.hardware.service.IAcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("ac")
@Slf4j
public class AcController {

    @Autowired
    private IAcService iAcService;

    @RequestMapping("updateConfig")
    @ResponseBody
    public int updateConfig(Wifi wifi){
        return iAcService.updateConfig(wifi);
    }

    @RequestMapping("toggleWifi")
    @ResponseBody
    public int toggleWifi(Wifi wifi){
        return iAcService.toggleWifi(wifi);
    }

    @RequestMapping("setServer")
    @ResponseBody
    public int setServer(Wifi wifi){
        log.info("==========="+wifi.getProbeMac());
        return iAcService.setServer(wifi);
    }

    @RequestMapping("requestSelect")
    @ResponseBody
    public ResponseData requestSelect(Wifi wifi){
        log.info("==========="+wifi.getProbeMac());
        ResponseData responseData = ResponseData.ResultFactory.makeOKResult();
        ProbeInfoResponse probeInfoResponse = new ProbeInfoResponse();
        List<ProbeInfo> list = new ArrayList();
        list.add(iAcService.requestSelect(wifi));
        probeInfoResponse.setRows(list);
        probeInfoResponse.setTotal(1);
        responseData.setData(probeInfoResponse);
        return responseData;
    }

    @RequestMapping("restart")
    @ResponseBody
    public int restart(Wifi wifi){
        return iAcService.restart(wifi);
    }
}
