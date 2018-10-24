package com.sunshineforce.hardware.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.domain.Probe;

import java.util.List;
import java.util.Map;

@JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
public class BraceletdataResponse extends Braceletdata {

    //探针列表
    private List<Braceletdata> rows;

    public List<Braceletdata> getRows() {
        return rows;
    }

    public void setRows(List<Braceletdata> rows) {
        this.rows = rows;
    }

    //数据条数
    private long total;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    private double location;

    public double getLocation() {
        return location;
    }

    public void setLocation(double location) {
        this.location = location;
    }
}
