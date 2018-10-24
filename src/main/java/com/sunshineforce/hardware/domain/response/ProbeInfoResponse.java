package com.sunshineforce.hardware.domain.response;

import com.sunshineforce.hardware.domain.ProbeInfo;

import java.util.List;

public class ProbeInfoResponse {
    //探针列表
    private List<ProbeInfo> rows;
    //数据条数
    private long total;

    public List<ProbeInfo> getRows() {
        return rows;
    }

    public void setRows(List<ProbeInfo> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
