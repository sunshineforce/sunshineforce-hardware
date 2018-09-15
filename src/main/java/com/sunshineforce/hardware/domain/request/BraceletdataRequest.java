package com.sunshineforce.hardware.domain.request;

import com.sunshineforce.hardware.domain.Braceletdata;

public class BraceletdataRequest extends Braceletdata {

    private Long beginTime;
    private Long endTime;
    private String probeMac;

    public BraceletdataRequest(Long beginTime, Long endTime, String probeMac) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.probeMac = probeMac;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String getProbeMac() {
        return probeMac;
    }

    @Override
    public void setProbeMac(String probeMac) {
        this.probeMac = probeMac;
    }
}
