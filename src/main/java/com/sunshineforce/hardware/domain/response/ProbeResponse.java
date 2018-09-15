package com.sunshineforce.hardware.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sunshineforce.hardware.domain.Probe;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
public class ProbeResponse extends Probe{

    //探针列表
    private List<ProbeResponse> rows;
    //数据条数
    private long total;

    private String statusStr;

    private String isNormalStr;

    private List<String> failList;

    public List<String> getFailList() {
        return failList;
    }

    public void setFailList(List<String> failList) {
        this.failList = failList;
    }

    public List<String> getSuccessList() {
        return successList;
    }

    public void setSuccessList(List<String> successList) {
        this.successList = successList;
    }

    private List<String> successList;

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getIsNormalStr() {
        return isNormalStr;
    }

    public void setIsNormalStr(String isNormalStr) {
        this.isNormalStr = isNormalStr;
    }

    public List<ProbeResponse> getRows() {
        return rows;
    }

    public void setRows(List<ProbeResponse> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
