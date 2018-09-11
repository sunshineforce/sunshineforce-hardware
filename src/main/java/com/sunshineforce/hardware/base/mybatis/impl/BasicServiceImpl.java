package com.sunshineforce.hardware.base.mybatis.impl;

import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunshineforce.hardware.base.mybatis.BasicService;
import com.sunshineforce.hardware.base.mybatis.IBasicMapper;
import com.sunshineforce.hardware.base.utils.Paging;
import org.springframework.http.HttpStatus;

import javax.annotation.Resource;
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

public abstract class BasicServiceImpl<T> implements BasicService<T> {

    @Resource
    protected Mapper<T> mapper;

    @Resource
    protected IBasicMapper<T> basicMapper;

    @Override
    public T queryOne(T record) {
        return mapper.selectOne(record);
    }

    @Override
    public List<T> queryAll(T record) {
        return mapper.select(record);
    }

    @Override
    public int queryCount(T record) {
        return mapper.selectCount(record);
    }

    @Override
    public T queryByPrimaryKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public int addSelective(T record) {
        return mapper.insertSelective(record);
    }

    @Override
    public int delete(T record) {
        return mapper.delete(record);
    }

    @Override
    public int deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int queryCountByExample(T example) {
        return mapper.selectCountByExample(example);
    }

    @Override
    public List<T> queryByExample(T example) {
        return mapper.selectByExample(example);
    }

    /**
     * 根据参数及分页信息查询列表
     * @param page 分页信息
     * @param params 查询参数
     * @return PageVo 返回对象
     */
    public <K, V> Paging selectPageByParams(Paging page, Map<K, V> params) {
        PageHelper.startPage(page.getPageIndex(), page.getPageCapacity(), true, false);

        List result = basicMapper.selectPageByMap(params);
        PageInfo pageInfo = new PageInfo(result);

        page.setTotalPages(pageInfo.getTotal());
        page.setPageIndex(pageInfo.getPageNum());
        page.setPageCapacity(pageInfo.getPageSize());
        page.setData(result);
        page.setCode(HttpStatus.OK.value());
        return page;
    }

}
