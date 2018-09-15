package com.sunshineforce.hardware.domain;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

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
public class Braceletdata implements Serializable {

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
    //舒张压
    private int diastolicPressure;
    //收缩压
    private int systolicPressure;
    //血氧
    private int bloodOxygen;
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

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getBloodOxygen() {
        return bloodOxygen;
    }

    public void setBloodOxygen(int bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
    }

    public int getHrv() {
        return hrv;
    }

    public void setHrv(int hrv) {
        this.hrv = hrv;
    }

    public long getUtc() {
        return utc;
    }

    public void setUtc(long utc) {
        this.utc = utc;
    }

    public int getStaticHeartRate() {
        return staticHeartRate;
    }

    public void setStaticHeartRate(int staticHeartRate) {
        this.staticHeartRate = staticHeartRate;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
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
