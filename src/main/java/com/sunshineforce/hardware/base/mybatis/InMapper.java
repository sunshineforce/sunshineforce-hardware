package com.sunshineforce.hardware.base.mybatis;

import com.sunshineforce.hardware.base.mybatis.impl.InProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * ProjectName: ssm-maven
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/6/27 16:05
 * ModifyUser: bjlixiaopeng
 * Class Description: common paging class
 * To change this template use File | Settings | File and Code Template
 */
public interface InMapper<T> {

    /**
     * 根据主键ids查询
     * @param list
     * @return
     */
    @SelectProvider(type = InProvider.class, method = "dynamicSQL")
    List<T> selectByInKey(List<T> list);

}