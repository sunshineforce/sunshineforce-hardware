package com.sunshineforce.hardware.domain.request;


public class SportPersonalRequest {

    private Integer memberId;
    private String startTime;
    private String endTime;
    private Long startTimeLong;
    private Long endTimeLong;
    private Integer skipModel;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getStartTimeLong() {
        return startTimeLong;
    }

    public void setStartTimeLong(Long startTimeLong) {
        this.startTimeLong = startTimeLong;
    }

    public Long getEndTimeLong() {
        return endTimeLong;
    }

    public void setEndTimeLong(Long endTimeLong) {
        this.endTimeLong = endTimeLong;
    }

    public Integer getSkipModel() {
        return skipModel;
    }

    public void setSkipModel(Integer skipModel) {
        this.skipModel = skipModel;
    }
}
