package com.sunshineforce.hardware.base.mybatis;


import com.github.abel533.mapper.Mapper;
import com.github.abel533.mapper.MySqlMapper;

/**
 * Created with IntelliJ IDEA
 * ProjectName: ssm-maven
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/6/27 16:05
 * ModifyUser: bjlixiaopeng
 * Class Description: common paging class
 * To change this template use File | Settings | File and Code Template
 */
public interface IBasicSetMapper<T> extends Mapper<T>,MySqlMapper<T>,InMapper<T> {
}
