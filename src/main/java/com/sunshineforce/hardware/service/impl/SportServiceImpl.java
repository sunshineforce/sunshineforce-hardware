package com.sunshineforce.hardware.service.impl;

import com.sunshineforce.hardware.dao.mapper.SportMapper;
import com.sunshineforce.hardware.domain.Sport;
import com.sunshineforce.hardware.domain.request.HeartRateRequest;
import com.sunshineforce.hardware.domain.request.SportClassRequest;
import com.sunshineforce.hardware.domain.request.SportPersonalRequest;
import com.sunshineforce.hardware.domain.request.SportStrengthRequest;
import com.sunshineforce.hardware.domain.response.HeartRateResponse;
import com.sunshineforce.hardware.domain.response.SportClassResponse;
import com.sunshineforce.hardware.domain.response.SportPersonalResponse;
import com.sunshineforce.hardware.domain.response.SportStrengthResponse;
import com.sunshineforce.hardware.service.ISportService;
import com.sunshineforce.hardware.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SportServiceImpl implements ISportService {

    @Autowired
    private SportMapper sportMapper;

    @Override
    public SportClassResponse getClassData(SportClassRequest sportClassRequest) {
        SportClassResponse sportClass = new SportClassResponse();

        //总人数
        List<String> probeMacList = sportMapper.getProbeMac(sportClassRequest);
        log.info("------------" + probeMacList.size());
        sportClass.setTotalMember(probeMacList.size());

        //区间
        try{
            if(!StringUtils.isEmpty(sportClassRequest.getStartTime()) && !StringUtils.isEmpty(sportClassRequest.getEndTime())){
                long startTimeLong = TimeUtil.getTimeDate(sportClassRequest.getStartTime(), TimeUtil.dataSecondString).getTime();
                long endTimeLong = TimeUtil.getTimeDate(sportClassRequest.getEndTime(), TimeUtil.dataSecondString).getTime();
                sportClassRequest.setStartTimeLong(startTimeLong);
                sportClassRequest.setEndTimeLong(endTimeLong);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }

        //男生人数
        sportClassRequest.setSex("男");
        List<String> manProbeMacList = sportMapper.getProbeMac(sportClassRequest);
        log.info("------------" + manProbeMacList.size());
        sportClass.setManTotalMember(manProbeMacList.size());

        //男生区间总次数
        Long manSkipNum = sportMapper.getSkipNumWithModel(sportClassRequest);
        log.info("------------"+manSkipNum);
        sportClass.setManSkipNum(manSkipNum);

        //计时模式，model=2，男生最优
        sportClassRequest.setSkipModel(2);
        Map<String, Object> manTimeModelBest = sportMapper.timeModelBest(sportClassRequest);
        if(manTimeModelBest == null){
            manTimeModelBest.put("name", "--");
            manTimeModelBest.put("skipTime", 0);
        }else{
            log.info("------------"+manTimeModelBest.get("name")+":"+manTimeModelBest.get("skipTime"));
        }
        sportClass.setManTimeModelBest(manTimeModelBest);


        //计数模式，model=1，男生最优
        sportClassRequest.setSkipModel(1);
        Map<String, Object> manNumModelBest = sportMapper.numModelBest(sportClassRequest);
        if(manNumModelBest == null){
            manNumModelBest.put("name", "--");
            manNumModelBest.put("skipNum", 0);
        }else{
            log.info("------------"+manNumModelBest.get("name")+":"+manNumModelBest.get("skipTime"));
        }
        sportClass.setManNumModelBest(manNumModelBest);

        //女生人数
        sportClassRequest.setSex("女");
        List<String> womanProbeMacList = sportMapper.getProbeMac(sportClassRequest);
        log.info("------------" + womanProbeMacList.size());
        sportClass.setWomanTotalMember(womanProbeMacList.size());

        //女生区间总次数
        Long womanSkipNum = sportMapper.getSkipNumWithModel(sportClassRequest);
        log.info("------------"+womanSkipNum);
        sportClass.setWomanSkipNum(womanSkipNum);

        //计时模式，model=2，女生最优
        sportClassRequest.setSkipModel(2);
        Map<String, Object> womanTimeModelBest = sportMapper.timeModelBest(sportClassRequest);
        if(womanTimeModelBest == null){
            womanTimeModelBest.put("name", "--");
            womanTimeModelBest.put("skipTime", 0);
        }else{
            log.info("------------"+womanTimeModelBest.get("name")+":"+womanTimeModelBest.get("skipTime"));
        }
        sportClass.setWomanTimeModelBest(womanTimeModelBest);


        //计数模式，model=1，女生最优
        sportClassRequest.setSkipModel(1);
        Map<String, Object> womanNumModelBest = sportMapper.numModelBest(sportClassRequest);
        if(womanNumModelBest == null){
            womanNumModelBest.put("name", "--");
            womanNumModelBest.put("skipNum", 0);
        }else{
            log.info("------------"+womanNumModelBest.get("name")+":"+womanNumModelBest.get("skipTime"));
        }
        sportClass.setWomanNumModelBest(womanNumModelBest);

        return sportClass;
    }

    @Override
    public SportPersonalResponse getPersonalData(SportPersonalRequest sportPersonalRequest) {
        //区间跳绳总数
        try{
            if(!StringUtils.isEmpty(sportPersonalRequest.getStartTime()) && !StringUtils.isEmpty(sportPersonalRequest.getEndTime())){
                long startTimeLong = TimeUtil.getTimeDate(sportPersonalRequest.getStartTime(), TimeUtil.dataSecondString).getTime();
                long endTimeLong = TimeUtil.getTimeDate(sportPersonalRequest.getEndTime(), TimeUtil.dataSecondString).getTime();
                sportPersonalRequest.setStartTimeLong(startTimeLong);
                sportPersonalRequest.setEndTimeLong(endTimeLong);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        SportPersonalResponse sportPersonal = new SportPersonalResponse();
        Integer personalTotalTime = sportMapper.getPersonalTotalTime(sportPersonalRequest);
        sportPersonal.setSkipTime(personalTotalTime);
        Long personalTotalNum = sportMapper.getPersonalTotalNum(sportPersonalRequest);
        sportPersonal.setSkipNum(personalTotalNum);
        Long groupNum = sportMapper.getSkipGroupNum(sportPersonalRequest);
        sportPersonal.setSkipGroup(groupNum);
        //计数模式
        sportPersonalRequest.setSkipModel(1);
        List<Integer> numGroup = sportMapper.getNumGroup(sportPersonalRequest);
        sportPersonal.setNumGroup(numGroup);
        //计时模式
        sportPersonalRequest.setSkipModel(2);
        List<Integer> timeGroup = sportMapper.getTimeGroup(sportPersonalRequest);
        sportPersonal.setTimeGroup(timeGroup);
        return sportPersonal;
    }

    @Override
    public HeartRateResponse getHeartRateData(HeartRateRequest heartRateRequest) {
        HeartRateResponse heartRateResponse = new HeartRateResponse();
        try{
            if(!StringUtils.isEmpty(heartRateRequest.getStartTime()) && !StringUtils.isEmpty(heartRateRequest.getEndTime())){
                long startTimeLong = TimeUtil.getTimeDate(heartRateRequest.getStartTime(), TimeUtil.dataSecondString).getTime();
                long endTimeLong = TimeUtil.getTimeDate(heartRateRequest.getEndTime(), TimeUtil.dataSecondString).getTime();
                heartRateRequest.setEndTimeLong(endTimeLong);
                heartRateRequest.setStartTimeLong(startTimeLong);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        heartRateResponse.setMaxHeartRate(sportMapper.getMaxHeartRate(heartRateRequest));
        heartRateResponse.setMinHeartRate(sportMapper.getMinHeartRate(heartRateRequest));
        heartRateResponse.setAveHeartRate(sportMapper.getAveHeartRate(heartRateRequest));
        Double staticHeartRate = heartRateRequest.getStaticHeartRate();
        if(staticHeartRate != null){
            heartRateRequest.setHeightHeartRate(staticHeartRate * 1.90);
            heartRateRequest.setLowHeartRate(staticHeartRate * 1.35);
            heartRateRequest.setMiddleHeartRate(staticHeartRate * 1.50);
            heartRateResponse.setHeightTime(sportMapper.getHeightTime(heartRateRequest));
            heartRateResponse.setLowTime(sportMapper.getLowTime(heartRateRequest));
            heartRateResponse.setMiddleTime(sportMapper.getMiddleTime(heartRateRequest));
            heartRateResponse.setTotalTime(sportMapper.getHeightTime(heartRateRequest) + sportMapper.getLowTime(heartRateRequest) + sportMapper.getMiddleTime(heartRateRequest));
        }
        return heartRateResponse;
    }

    @Override
    public HeartRateResponse getClassHeartRateData(HeartRateRequest heartRateRequest) {
        HeartRateResponse result = getHeartRateData(heartRateRequest);
        List<HeartRateResponse> heartRateResponseList = new ArrayList<>();
        List<Integer> memberIdList = sportMapper.getMemberIdByClassQunId(heartRateRequest.getClassQunId());
        for(Integer memberId : memberIdList){
            heartRateRequest.setMemberId(memberId);
            HeartRateResponse heartRateResponse = getHeartRateData(heartRateRequest);
            heartRateResponseList.add(heartRateResponse);
        }
        int size = memberIdList.size();
        int totalAvgHeartRate = 0;
        Double totalHeightTime = 0.0;
        Double totalLowTime = 0.0;
        Double totalMiddleTime = 0.0;
        Double totalTime = 0.0;
        int maxHeartRate = 0;
        int minHeartRate = 0;
        for(HeartRateResponse heartRateResponse : heartRateResponseList){
            totalAvgHeartRate = totalAvgHeartRate + heartRateResponse.getAveHeartRate();
            totalHeightTime = totalHeightTime + heartRateResponse.getHeightTime();
            totalLowTime = totalLowTime + heartRateResponse.getLowTime();
            totalMiddleTime = totalMiddleTime + heartRateResponse.getMiddleTime();
            totalTime = totalTime + heartRateResponse.getTotalTime();
            maxHeartRate = maxHeartRate + heartRateResponse.getMaxHeartRate();
            minHeartRate = minHeartRate + heartRateResponse.getMinHeartRate();
        }
        result.setTotalTime(totalTime);
        result.setAveHeartRate(totalAvgHeartRate/size);
        result.setMaxHeartRate(maxHeartRate/size);
        result.setMinHeartRate(minHeartRate/size);
        result.setHeightTime(totalHeightTime);
        result.setMiddleTime(totalMiddleTime);
        result.setLowTime(totalLowTime);
        return result;
    }

    @Override
    public SportStrengthResponse getPersonalSportStrength(SportStrengthRequest sportStrengthRequest) {
        SportStrengthResponse sportStrengthResponse = new SportStrengthResponse();
        HeartRateRequest heartRateRequest = new HeartRateRequest();
        heartRateRequest.setMemberId(sportStrengthRequest.getMemberId());
        heartRateRequest.setStaticHeartRate(sportStrengthRequest.getStaticHeartRate());
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Long time = 0l;
        try{
            if(!StringUtils.isEmpty(sportStrengthRequest.getStartTime()) && !StringUtils.isEmpty(sportStrengthRequest.getEndTime())){
                long startTimeLong = TimeUtil.getTimeDate(sportStrengthRequest.getStartTime(), TimeUtil.dataSecondString).getTime();
                long endTimeLong = TimeUtil.getTimeDate(sportStrengthRequest.getEndTime(), TimeUtil.dataSecondString).getTime();
                time = (endTimeLong - startTimeLong)/60;
                heartRateRequest.setEndTimeLong(endTimeLong);
                heartRateRequest.setStartTimeLong(startTimeLong);
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        HeartRateResponse heartRateResponse = getHeartRateData(heartRateRequest);
        Double heartRateStrength = heartRateResponse.getAveHeartRate()/sportStrengthRequest.getStaticHeartRate();
        sportStrengthResponse.setHeartRateStrength(heartRateStrength);
        if(heartRateStrength >= 2){
            sportStrengthResponse.setHeartRateStrengthIndex("最大");
        }
        if(heartRateStrength >= 1.8 && heartRateStrength < 2){
            sportStrengthResponse.setHeartRateStrengthIndex("较大");
        }
        if(heartRateStrength >= 1.5 && heartRateStrength < 1.8){
            sportStrengthResponse.setHeartRateStrengthIndex("中等");
        }
        if(heartRateStrength >= 1.2 && heartRateStrength < 1.5){
            sportStrengthResponse.setHeartRateStrengthIndex("较小");
        }
        Double sportDensity = heartRateResponse.getTotalTime() / time * 100;
        sportStrengthResponse.setSportDensity(decimalFormat.format(sportDensity) + "%");
        if(sportDensity >= 40){
            sportStrengthResponse.setSportDensityIndex("较大");
        }
        if(sportDensity >= 30 && sportDensity < 40){
            sportStrengthResponse.setSportDensityIndex("适宜");
        }
        if(sportDensity > 0 && sportDensity < 30){
            sportStrengthResponse.setSportDensityIndex("较小");
        }
        if(sportDensity == 0){
            sportStrengthResponse.setSportDensityIndex("无运动数据");
        }
        sportStrengthResponse.setLowHeartRateDensity(decimalFormat.format((heartRateResponse.getLowTime()/time) * 100) + "%");
        sportStrengthResponse.setMiddleHeartRateDensity(decimalFormat.format((heartRateResponse.getMiddleTime()/time) * 100) + "%");
        sportStrengthResponse.setHeightHeartRateDensity(decimalFormat.format((heartRateResponse.getHeightTime()/time) * 100) + "%");
        return sportStrengthResponse;
    }

    @Override
    public SportStrengthResponse getClassSportStrength(SportStrengthRequest sportStrengthRequest) {
        SportStrengthResponse result = new SportStrengthResponse();
        List<Integer> memberIdList = sportMapper.getMemberIdByClassQunId(sportStrengthRequest.getClassQunId());
        List<SportStrengthResponse> sportStrengthResponsesList = new ArrayList<>();
        for(Integer memberId : memberIdList){
            sportStrengthRequest.setMemberId(memberId);
            SportStrengthResponse sportStrengthResponse = getPersonalSportStrength(sportStrengthRequest);
            sportStrengthResponsesList.add(sportStrengthResponse);
        }

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        Double heartRateStrength = 0.0;
        Double sportDensity = 0.0;
        Double lowHeartRateDensty = 0.0;
        Double middleHeartRateDensty = 0.0;
        Double heightHeartRateDensty = 0.0;
        for(SportStrengthResponse sportStrengthResponse : sportStrengthResponsesList){
            heartRateStrength = heartRateStrength + sportStrengthResponse.getHeartRateStrength();
            sportDensity = sportDensity + Double.parseDouble(sportStrengthResponse.getSportDensity().substring(0, sportStrengthResponse.getSportDensity().length() - 1));
            lowHeartRateDensty = lowHeartRateDensty + Double.parseDouble(sportStrengthResponse.getLowHeartRateDensity().substring(0, sportStrengthResponse.getLowHeartRateDensity().length() - 1));
            middleHeartRateDensty = middleHeartRateDensty + Double.parseDouble(sportStrengthResponse.getMiddleHeartRateDensity().substring(0, sportStrengthResponse.getMiddleHeartRateDensity().length() - 1));
            heightHeartRateDensty = heightHeartRateDensty + Double.parseDouble(sportStrengthResponse.getHeightHeartRateDensity().substring(0, sportStrengthResponse.getHeightHeartRateDensity().length() - 1));

        }
        result.setLowHeartRateDensity(decimalFormat.format(lowHeartRateDensty/memberIdList.size())+"%");
        result.setMiddleHeartRateDensity(decimalFormat.format((middleHeartRateDensty/memberIdList.size()))+"%");
        result.setHeightHeartRateDensity(decimalFormat.format((heightHeartRateDensty/memberIdList.size()))+"%");
        result.setHeartRateStrength(heartRateStrength/memberIdList.size());
        if(heartRateStrength >= 2){
            result.setHeartRateStrengthIndex("最大");
        }
        if(heartRateStrength >= 1.8 && heartRateStrength < 2){
            result.setHeartRateStrengthIndex("较大");
        }
        if(heartRateStrength >= 1.5 && heartRateStrength < 1.8){
            result.setHeartRateStrengthIndex("中等");
        }
        if(heartRateStrength >= 1.2 && heartRateStrength < 1.5){
            result.setHeartRateStrengthIndex("较小");
        }
        result.setSportDensity(decimalFormat.format((sportDensity/memberIdList.size())) + "%");
        if(sportDensity >= 40){
            result.setSportDensityIndex("较大");
        }
        if(sportDensity >= 30 && sportDensity < 40){
            result.setSportDensityIndex("适宜");
        }
        if(sportDensity > 0 && sportDensity < 30){
            result.setSportDensityIndex("较小");
        }
        if(sportDensity == 0){
            result.setSportDensityIndex("无运动数据");
        }
        return result;
    }
}
