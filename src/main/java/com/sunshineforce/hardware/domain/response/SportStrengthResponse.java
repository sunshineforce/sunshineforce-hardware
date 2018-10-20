package com.sunshineforce.hardware.domain.response;

public class SportStrengthResponse {
    private Double heartRateStrength;
    private String heartRateStrengthIndex;
    private String sportDensity;
    private String sportDensityIndex;
    private String lowHeartRateDensity;
    private String middleHeartRateDensity;
    private String heightHeartRateDensity;

    public Double getHeartRateStrength() {
        return heartRateStrength;
    }

    public void setHeartRateStrength(Double heartRateStrength) {
        this.heartRateStrength = heartRateStrength;
    }

    public String getHeartRateStrengthIndex() {
        return heartRateStrengthIndex;
    }

    public void setHeartRateStrengthIndex(String heartRateStrengthIndex) {
        this.heartRateStrengthIndex = heartRateStrengthIndex;
    }

    public String getSportDensity() {
        return sportDensity;
    }

    public void setSportDensity(String sportDensity) {
        this.sportDensity = sportDensity;
    }

    public String getSportDensityIndex() {
        return sportDensityIndex;
    }

    public void setSportDensityIndex(String sportDensityIndex) {
        this.sportDensityIndex = sportDensityIndex;
    }

    public String getLowHeartRateDensity() {
        return lowHeartRateDensity;
    }

    public void setLowHeartRateDensity(String lowHeartRateDensity) {
        this.lowHeartRateDensity = lowHeartRateDensity;
    }

    public String getMiddleHeartRateDensity() {
        return middleHeartRateDensity;
    }

    public void setMiddleHeartRateDensity(String middleHeartRateDensity) {
        this.middleHeartRateDensity = middleHeartRateDensity;
    }

    public String getHeightHeartRateDensity() {
        return heightHeartRateDensity;
    }

    public void setHeightHeartRateDensity(String heightHeartRateDensity) {
        this.heightHeartRateDensity = heightHeartRateDensity;
    }
}
