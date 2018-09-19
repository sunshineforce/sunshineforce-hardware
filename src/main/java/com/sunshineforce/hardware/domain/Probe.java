package com.sunshineforce.hardware.domain;

import com.sunshineforce.hardware.domain.request.Page;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_probe")
public class Probe extends Page implements Serializable {
    private static final long serialVersionUID = -8555535280590699347L;

    private int id;
    private String probeMac;
    //状态
    private Integer status;
    //位置
    private String location;
    //是否运行正常
    private int isNormal;
    //实时吞吐量
    private Long regularThroughput;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(int isNormal) {
        this.isNormal = isNormal;
    }

    public String getProbeMac() {
        return probeMac;
    }

    public Long getRegularThroughput() {
        return regularThroughput;
    }

    public void setRegularThroughput(Long regularThroughput) {
        this.regularThroughput = regularThroughput;
    }

    public void setProbeMac(String probeMac) {
        this.probeMac = probeMac;
    }
}
