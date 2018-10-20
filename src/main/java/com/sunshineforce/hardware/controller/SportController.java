package com.sunshineforce.hardware.controller;

import com.sunshineforce.hardware.domain.request.HeartRateRequest;
import com.sunshineforce.hardware.domain.request.SportClassRequest;
import com.sunshineforce.hardware.domain.request.SportPersonalRequest;
import com.sunshineforce.hardware.domain.request.SportStrengthRequest;
import com.sunshineforce.hardware.domain.response.HeartRateResponse;
import com.sunshineforce.hardware.domain.response.SportClassResponse;
import com.sunshineforce.hardware.domain.response.SportPersonalResponse;
import com.sunshineforce.hardware.domain.response.SportStrengthResponse;
import com.sunshineforce.hardware.service.ISportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sport")
public class SportController {
    @Autowired
    private ISportService iSportService;

    @RequestMapping("getClassData")
    @ResponseBody
    public SportClassResponse getClassData(SportClassRequest sportClassRequest){
        sportClassRequest.setSkipModel(1);
        sportClassRequest.setClassQunId(2);
        sportClassRequest.setSex("女");
        sportClassRequest.setStartTime("2018-10-14 20:00:00");
        sportClassRequest.setEndTime("2018-10-14 21:00:00");
        return iSportService.getClassData(sportClassRequest);
    }

    @RequestMapping("getPersonalData")
    @ResponseBody
    public SportPersonalResponse getPersonalData(SportPersonalRequest sportPersonalRequest){
        sportPersonalRequest.setSkipModel(1);
        sportPersonalRequest.setMemberId(2);
        sportPersonalRequest.setStartTime("2018-10-14 20:00:00");
        sportPersonalRequest.setEndTime("2018-10-14 21:00:00");
        return iSportService.getPersonalData(sportPersonalRequest);
    }

    @RequestMapping("getPersonalHeartRateData")
    @ResponseBody
    public HeartRateResponse getPersonalHeartRateData(HeartRateRequest heartBeatRequest){
        heartBeatRequest.setStaticHeartRate(60.0);
        heartBeatRequest.setMemberId(2);
        heartBeatRequest.setStartTime("2018-10-14 20:00:00");
        heartBeatRequest.setEndTime("2018-10-14 21:00:00");
        return iSportService.getHeartRateData(heartBeatRequest);
    }

    @RequestMapping("getClassHeartRateData")
    @ResponseBody
    public HeartRateResponse getClassHeartRateData(HeartRateRequest heartBeatRequest){
        heartBeatRequest.setStaticHeartRate(60.0);
        heartBeatRequest.setClassQunId(2);
        heartBeatRequest.setStartTime("2018-10-14 20:00:00");
        heartBeatRequest.setEndTime("2018-10-14 21:00:00");
        return iSportService.getClassHeartRateData(heartBeatRequest);
    }

    @RequestMapping("getPersonalSportStrength")
    @ResponseBody
    public SportStrengthResponse getPersonalSportStrength(SportStrengthRequest sportStrengthRequest){
        sportStrengthRequest.setStaticHeartRate(60.0);
        sportStrengthRequest.setMemberId(2);
        sportStrengthRequest.setStartTime("2018-10-14 20:00:00");
        sportStrengthRequest.setEndTime("2018-10-14 21:00:00");
        return iSportService.getPersonalSportStrength(sportStrengthRequest);
    }

    @RequestMapping("getClassSportStrength")
    @ResponseBody
    public SportStrengthResponse getClassSportStrength(SportStrengthRequest sportStrengthRequest){
        sportStrengthRequest.setStaticHeartRate(60.0);
        sportStrengthRequest.setClassQunId(2);
        sportStrengthRequest.setStartTime("2018-10-14 20:00:00");
        sportStrengthRequest.setEndTime("2018-10-14 21:00:00");
        return iSportService.getClassSportStrength(sportStrengthRequest);
    }
}
