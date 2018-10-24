package com.sunshineforce.hardware.domain.response;

import com.sunshineforce.hardware.domain.Location;
import com.sunshineforce.hardware.domain.ProbeInfo;

import java.util.List;

public class LocationResponse {
    //探针列表
    private List<Location> rows;
    //数据条数
    private long total;

    public List<Location> getRows() {
        return rows;
    }

    public void setRows(List<Location> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
