package com.sunshineforce.hardware.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sunshineforce.hardware.domain.request.Page;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * ProjectName: ssm-maven
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/6/26 17:57
 * ModifyUser: bjlixiaopeng
 * Class Description:
 * To change this template use File | Settings | File and Code Template
 */

@Table(name = "t_braceletdata")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Braceletdata extends Page implements Serializable {

    private static final long serialVersionUID = -8555535280590699348L;

    //探针mac
    private String probeMac;
    //手环mac
    private String braceletMac;
    //心率
    private int heartRate;
    //步数
    private int step;
    //活动状态
    private String active;
    //睡眠状态
    private String sleep;
    //跳绳模式
    private int skipModel;
    //跳绳数量
    private int skipNum;
    //跳绳时长
    private int skipTime;
    //hrv
    private int hrv;
    //上传数据utc
    private Long utc;
    //静止心率
    private int staticHeartRate;
    //uuid
    private String uuid;
    //添加时间
    private Long addTime;
    //心跳
    private int heartBeat;
    //点亮
    private int battery;

    private Integer signalValue;

    public Integer getSignalValue() {
        return signalValue;
    }

    public void setSignalValue(Integer signalValue) {
        this.signalValue = signalValue;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getUtc() {
        return utc;
    }

    public Long getAddTime() {
        return addTime;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public void setUtc(Long utc) {
        this.utc = utc;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public int getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(int heartBeat) {
        this.heartBeat = heartBeat;
    }

    public String getProbeMac() {
        return probeMac;
    }

    public void setProbeMac(String probeMac) {
        this.probeMac = probeMac;
    }

    public String getBraceletMac() {
        return braceletMac;
    }

    public void setBraceletMac(String braceletMac) {
        this.braceletMac = braceletMac;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public int getSkipModel() {
        return skipModel;
    }

    public void setSkipModel(int skipModel) {
        this.skipModel = skipModel;
    }

    public int getSkipNum() {
        return skipNum;
    }

    public void setSkipNum(int skipNum) {
        this.skipNum = skipNum;
    }

    public int getSkipTime() {
        return skipTime;
    }

    public void setSkipTime(int skipTime) {
        this.skipTime = skipTime;
    }

    public int getHrv() {
        return hrv;
    }

    public void setHrv(int hrv) {
        this.hrv = hrv;
    }

    public int getStaticHeartRate() {
        return staticHeartRate;
    }

    public void setStaticHeartRate(int staticHeartRate) {
        this.staticHeartRate = staticHeartRate;
    }

/*   @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", createTime=" + createTime +
                ", dataStatus=" + dataStatus +
                '}';
    }*/
}
