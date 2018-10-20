package com.sunshineforce.hardware.domain.request;

public class SportStrengthRequest {
    private Double staticHeartRate;
    private Integer memberId;
    private Integer classQunId;
    private String startTime;
    private String endTime;
    private Long startTimeLong;
    private Long endTimeLong;

    public Double getStaticHeartRate() {
        return staticHeartRate;
    }

    public void setStaticHeartRate(Double staticHeartRate) {
        this.staticHeartRate = staticHeartRate;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getClassQunId() {
        return classQunId;
    }

    public void setClassQunId(Integer classQunId) {
        this.classQunId = classQunId;
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
}
