package com.sunshineforce.hardware.base.mybatis;


import com.github.abel533.mapper.Mapper;
import com.github.abel533.mapper.MySqlMapper;

/**
 * Created with IntelliJ IDEA
 * ProjectName: sunshineforce-hardware
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/9/19 18:33
 * ModifyUser: bjlixiaopeng
 * Class Description:
 * To change this template use File | Settings | File and Code Template
 */

public interface IBasicSetMapper<T> extends Mapper<T>,MySqlMapper<T>,InMapper<T> {
}
