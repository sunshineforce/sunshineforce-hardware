package com.sunshineforce.hardware.base.mybatis;

import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * ProjectName: ssm-maven
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/6/27 16:05
 * ModifyUser: bjlixiaopeng
 * Class Description: common paging class
 * To change this template use File | Settings | File and Code Template
 */
public interface IBasicMapper<T> extends Mapper<T> {

	<K,V> List<T> selectPageByMap(Map<K, V> map);
	
}
