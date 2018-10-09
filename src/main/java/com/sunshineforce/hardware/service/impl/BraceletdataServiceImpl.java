package com.sunshineforce.hardware.service.impl;

import com.github.abel533.entity.Example;
import com.sunshineforce.hardware.base.mybatis.impl.BasicSetServiceImpl;
import com.sunshineforce.hardware.dao.mapper.BraceletdataMapper;
import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.domain.request.BraceletdataRequest;
import com.sunshineforce.hardware.service.IBraceletdataService;
import lombok.extern.slf4j.Slf4j;
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
    public List<Braceletdata> selectBraceletdatas() {
        return null;
    }

    @Override
    public long countBraceletdata(BraceletdataRequest braceletdataRequest) {
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
        System.out.println("----------"+proheMac);
        if(proheMac != null){
            criteria.andEqualTo("probeMac", proheMac);
        }
        return braceletdataMapper.selectCountByExample(braceletdataExample);
    }
}
