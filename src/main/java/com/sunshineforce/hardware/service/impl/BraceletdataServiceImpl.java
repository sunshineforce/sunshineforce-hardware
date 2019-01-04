package com.sunshineforce.hardware.service.impl;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunshineforce.hardware.base.mybatis.impl.BasicSetServiceImpl;
import com.sunshineforce.hardware.dao.mapper.BraceletdataMapper;
import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.domain.Probe;
import com.sunshineforce.hardware.domain.request.BraceletdataRequest;
import com.sunshineforce.hardware.service.IBraceletdataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BraceletdataServiceImpl extends BasicSetServiceImpl<Braceletdata> implements IBraceletdataService {

    @Autowired
    private BraceletdataMapper braceletdataMapper;

    @Override
    public List<Braceletdata> selectBraceletdatas(BraceletdataRequest braceletdataRequest) {
        Example braceletdataExample = buildExample(braceletdataRequest);
        String orderName = Optional.ofNullable(braceletdataRequest.getOrderName()).orElse("id");
        String orderType = Optional.ofNullable(braceletdataRequest.getOrderType()).orElse("asc");
        PageHelper.startPage(braceletdataRequest.getCurrentPage(), braceletdataRequest.getPageSize(), orderName + " " + orderType);
        List<Braceletdata> braceletdataList = braceletdataMapper.selectByExample(braceletdataExample);
        PageInfo<Braceletdata> braceletdataPageInfo = new PageInfo<Braceletdata>(braceletdataList);
        return braceletdataPageInfo.getList();
    }

    @Override
    public List<Braceletdata> getBraceletdatasList(BraceletdataRequest braceletdataRequest) {
        Example braceletdataExample = buildExample(braceletdataRequest);
        List<Braceletdata> braceletdataList = braceletdataMapper.selectByExample(braceletdataExample);
        return braceletdataList;
    }

    @Override
    public List<Braceletdata> selectList() {
        return braceletdataMapper.selectList();
    }

    @Override
    public long countBraceletdata(BraceletdataRequest braceletdataRequest) {
        Example braceletdataExample = buildExample(braceletdataRequest);
        return braceletdataMapper.selectCountByExample(braceletdataExample);
    }

    private Example buildExample(BraceletdataRequest braceletdataRequest){
        Example braceletdataExample = new Example(BraceletdataRequest.class);
        Example.Criteria criteria = braceletdataExample.createCriteria();
        long beginTime = Optional.ofNullable(braceletdataRequest.getBeginTime()).orElse(0l);
        if(beginTime != 0 && beginTime > 0){
            criteria.andGreaterThan("addTime", beginTime);
        }
        long endTime = Optional.ofNullable(braceletdataRequest.getEndTime()).orElse(0l);
        if(endTime != 0 && endTime > 0){
            criteria.andLessThan("addTime", endTime);
        }
        String proheMac = Optional.ofNullable(braceletdataRequest.getProbeMac()).orElse(null);
        if(proheMac != null){
            criteria.andEqualTo("probeMac", proheMac);
        }
        String braceletMac = Optional.ofNullable(braceletdataRequest.getBraceletMac()).orElse(null);
        if(braceletMac != null){
            criteria.andEqualTo("braceletMac", braceletMac);
        }
        return braceletdataExample;
    }
}
