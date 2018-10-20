package com.sunshineforce.hardware.domain.response;

public class HeartRateResponse {

    private Integer aveHeartRate;
    private Integer maxHeartRate;
    private Integer minHeartRate;
    private Double lowTime;
    private Double middleTime;
    private Double heightTime;
    private Double totalTime;

    public Integer getAveHeartRate() {
        return aveHeartRate;
    }

    public void setAveHeartRate(Integer aveHeartRate) {
        this.aveHeartRate = aveHeartRate;
    }

    public Integer getMaxHeartRate() {
        return maxHeartRate;
    }

    public void setMaxHeartRate(Integer maxHeartRate) {
        this.maxHeartRate = maxHeartRate;
    }

    public Integer getMinHeartRate() {
        return minHeartRate;
    }

    public void setMinHeartRate(Integer minHeartRate) {
        this.minHeartRate = minHeartRate;
    }

    public Double getLowTime() {
        return lowTime;
    }

    public void setLowTime(Double lowTime) {
        this.lowTime = lowTime;
    }

    public Double getMiddleTime() {
        return middleTime;
    }

    public void setMiddleTime(Double middleTime) {
        this.middleTime = middleTime;
    }

    public Double getHeightTime() {
        return heightTime;
    }

    public void setHeightTime(Double heightTime) {
        this.heightTime = heightTime;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTime = totalTime;
    }
}
