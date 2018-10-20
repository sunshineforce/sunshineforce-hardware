package com.sunshineforce.hardware.domain.request;

public class HeartRateRequest {

    private Integer classQunId;
    private Integer memberId;
    private String startTime;
    private String endTime;
    private Long startTimeLong;
    private Long endTimeLong;
    private Double staticHeartRate;
    private Double lowHeartRate;
    private Double middleHeartRate;
    private Double heightHeartRate;

    public Integer getClassQunId() {
        return classQunId;
    }

    public void setClassQunId(Integer classQunId) {
        this.classQunId = classQunId;
    }

    public Double getLowHeartRate() {
        return lowHeartRate;
    }

    public void setLowHeartRate(Double lowHeartRate) {
        this.lowHeartRate = lowHeartRate;
    }

    public Double getMiddleHeartRate() {
        return middleHeartRate;
    }

    public void setMiddleHeartRate(Double middleHeartRate) {
        this.middleHeartRate = middleHeartRate;
    }

    public Double getHeightHeartRate() {
        return heightHeartRate;
    }

    public void setHeightHeartRate(Double heightHeartRate) {
        this.heightHeartRate = heightHeartRate;
    }

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
