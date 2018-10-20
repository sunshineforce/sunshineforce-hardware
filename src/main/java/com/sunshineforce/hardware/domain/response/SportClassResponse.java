package com.sunshineforce.hardware.domain.response;

import java.util.Map;

public class SportClassResponse {
    //Z总人数
    private Integer totalMember;

    //男生人数人数
    private Integer manTotalMember;

    //女生人数
    private Integer womanTotalMember;

    //男生区间跳绳总次数
    private Long manSkipNum;

    //女生区间跳绳总次数
    private Long womanSkipNum;

    //计时模式用时最少的男生
    private Map<String, Object> manTimeModelBest;

    //计数模式跳的最多的男生
    private Map<String, Object> manNumModelBest;

    //计时模式用时最少的男生
    private Map<String, Object> womanTimeModelBest;

    //计数模式跳的最多的男生
    private Map<String, Object> womanNumModelBest;

    public Map<String, Object> getManTimeModelBest() {
        return manTimeModelBest;
    }

    public void setManTimeModelBest(Map<String, Object> manTimeModelBest) {
        this.manTimeModelBest = manTimeModelBest;
    }

    public Map<String, Object> getManNumModelBest() {
        return manNumModelBest;
    }

    public void setManNumModelBest(Map<String, Object> manNumModelBest) {
        this.manNumModelBest = manNumModelBest;
    }

    public Map<String, Object> getWomanTimeModelBest() {
        return womanTimeModelBest;
    }

    public void setWomanTimeModelBest(Map<String, Object> womanTimeModelBest) {
        this.womanTimeModelBest = womanTimeModelBest;
    }

    public Map<String, Object> getWomanNumModelBest() {
        return womanNumModelBest;
    }

    public void setWomanNumModelBest(Map<String, Object> womanNumModelBest) {
        this.womanNumModelBest = womanNumModelBest;
    }

    public Integer getManTotalMember() {
        return manTotalMember;
    }

    public void setManTotalMember(Integer manTotalMember) {
        this.manTotalMember = manTotalMember;
    }

    public Integer getWomanTotalMember() {
        return womanTotalMember;
    }

    public void setWomanTotalMember(Integer womanTotalMember) {
        this.womanTotalMember = womanTotalMember;
    }

    public Integer getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(Integer totalMember) {
        this.totalMember = totalMember;
    }

    public Long getManSkipNum() {
        return manSkipNum;
    }

    public void setManSkipNum(Long manSkipNum) {
        this.manSkipNum = manSkipNum;
    }

    public Long getWomanSkipNum() {
        return womanSkipNum;
    }

    public void setWomanSkipNum(Long womanSkipNum) {
        this.womanSkipNum = womanSkipNum;
    }
}
