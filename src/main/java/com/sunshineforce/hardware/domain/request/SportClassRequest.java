package com.sunshineforce.hardware.domain.request;


public class SportClassRequest {

    private Integer classQunId;
    private String sex;
    private String startTime;
    private String endTime;
    private Long startTimeLong;
    private Long endTimeLong;
    private Integer skipModel;

    public Integer getSkipModel() {
        return skipModel;
    }

    public void setSkipModel(Integer skipModel) {
        this.skipModel = skipModel;
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

    public Integer getClassQunId() {
        return classQunId;
    }

    public void setClassQunId(Integer classQunId) {
        this.classQunId = classQunId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
