package com.sunshineforce.hardware.domain;

import java.util.Map;

public class Sport {

    //Z总人数
    private Integer totalMember;

    //区间跳绳总次数
    private Long skipNum;

    //计时模式用时最少的人带性别
    private Map<String, Object> timeModelBest;

    //计数模式跳的最多的人带性别
    private Map<String, Object> numModelBest;

    public Integer getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(Integer totalMember) {
        this.totalMember = totalMember;
    }

    public Long getSkipNum() {
        return skipNum;
    }

    public void setSkipNum(Long skipNum) {
        this.skipNum = skipNum;
    }

    public Map<String, Object> getTimeModelBest() {
        return timeModelBest;
    }

    public void setTimeModelBest(Map<String, Object> timeModelBest) {
        this.timeModelBest = timeModelBest;
    }

    public Map<String, Object> getNumModelBest() {
        return numModelBest;
    }

    public void setNumModelBest(Map<String, Object> numModelBest) {
        this.numModelBest = numModelBest;
    }
}
