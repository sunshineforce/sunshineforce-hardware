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

@Table(name = "t_bluetooth")
public class Bluetooth implements Serializable {

    private static final long serialVersionUID = -8555535280590699348L;

    //蓝牙mac
    private String bluetoothMac;
    //无限设备mac
    private String wifiMac;
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
    private long utc;
    //静止心率
    private int staticHeartRate;

    public Bluetooth() {
    }

    public Bluetooth(String bluetoothMac, String wifiMac, int heartRate, int step, String active, String sleep, int diastolicPressure, int systolicPressure, int bloodOxygen, int hrv, long utc, int staticHeartRate) {
        this.bluetoothMac = bluetoothMac;
        this.wifiMac = wifiMac;
        this.heartRate = heartRate;
        this.step = step;
        this.active = active;
        this.sleep = sleep;
        this.diastolicPressure = diastolicPressure;
        this.systolicPressure = systolicPressure;
        this.bloodOxygen = bloodOxygen;
        this.hrv = hrv;
        this.utc = utc;
        this.staticHeartRate = staticHeartRate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBluetoothMac() {
        return bluetoothMac;
    }

    public void setBluetoothMac(String bluetoothMac) {
        this.bluetoothMac = bluetoothMac;
    }

    public String getWifiMac() {
        return wifiMac;
    }

    public void setWifiMac(String wifiMac) {
        this.wifiMac = wifiMac;
    }

    public int getHeartRate() {
        return heartRate;
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
