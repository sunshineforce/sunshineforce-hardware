package com.sunshineforce.hardware.service;

import com.sunshineforce.hardware.base.mybatis.IBasicSetMapper;
import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.domain.Probe;
import com.sunshineforce.hardware.domain.response.ProbeResponse;

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


public interface IProbeService extends IBasicSetMapper<Probe> {
    List<ProbeResponse> selectProbes(Probe probe);
    long countProbes(Probe probe);
    int addProbe(Probe probe);
    ProbeResponse deleteProbeByIds(String ids);
}
