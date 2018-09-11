package com.sunshineforce.hardware.service;

import com.sunshineforce.hardware.base.mybatis.IBasicSetMapper;
import com.sunshineforce.hardware.domain.Bluetooth;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * ProjectName: ssm-maven
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/6/27 18:04
 * ModifyUser: bjlixiaopeng
 * Class Description:
 * To change this template use File | Settings | File and Code Template
 */


public interface IBluetoothService extends IBasicSetMapper<Bluetooth> {
    List<Bluetooth> selectUsers();
}
