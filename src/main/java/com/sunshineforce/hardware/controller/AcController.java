package com.sunshineforce.hardware.controller;

import com.sunshineforce.hardware.domain.Wifi;
import com.sunshineforce.hardware.service.IAcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("ac")
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
}
