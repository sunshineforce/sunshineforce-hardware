package com.sunshineforce.hardware.service.impl;

import com.sunshineforce.hardware.base.mybatis.impl.BasicSetServiceImpl;
import com.sunshineforce.hardware.dao.mapper.UserMapper;
import com.sunshineforce.hardware.domain.User;
import com.sunshineforce.hardware.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA
 * ProjectName: sunshineforce-hardware
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/9/19 18:42
 * ModifyUser: bjlixiaopeng
 * Class Description:
 * To change this template use File | Settings | File and Code Template
 */

@Service
public class UserServiceImpl extends BasicSetServiceImpl<User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByName(String username,String password) {
        User user = userMapper.selectOne(new User(username,password));
        return user;
    }
}
