package com.sunshineforce.hardware.domain.response;

import java.util.List;

public class SportPersonalResponse {
    private Integer skipTime;
    private Long skipNum;
    private Long skipGroup;
    private List<Integer> timeGroup;
    private List<Integer> numGroup;

    public Integer getSkipTime() {
        return skipTime;
    }

    public void setSkipTime(Integer skipTime) {
        this.skipTime = skipTime;
    }

    public Long getSkipNum() {
        return skipNum;
    }

    public void setSkipNum(Long skipNum) {
        this.skipNum = skipNum;
    }

    public Long getSkipGroup() {
        return skipGroup;
    }

    public void setSkipGroup(Long skipGroup) {
        this.skipGroup = skipGroup;
    }

    public List<Integer> getTimeGroup() {
        return timeGroup;
    }

    public void setTimeGroup(List<Integer> timeGroup) {
        this.timeGroup = timeGroup;
    }

    public List<Integer> getNumGroup() {
        return numGroup;
    }

    public void setNumGroup(List<Integer> numGroup) {
        this.numGroup = numGroup;
    }
}
