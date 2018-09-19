package com.sunshineforce.hardware.service;

import com.sunshineforce.hardware.base.mybatis.IBasicSetMapper;
import com.sunshineforce.hardware.domain.User;

/**
 * Created with IntelliJ IDEA
 * ProjectName: sunshineforce-hardware
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/9/19 18:33
 * ModifyUser: bjlixiaopeng
 * Class Description:
 * To change this template use File | Settings | File and Code Template
 */

public interface IUserService extends IBasicSetMapper<User> {
    User queryUserByName(String username,String password);
}
