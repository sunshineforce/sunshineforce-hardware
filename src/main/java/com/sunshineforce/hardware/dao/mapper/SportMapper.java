package com.sunshineforce.hardware.dao.mapper;

import com.github.abel533.mapper.Mapper;
import com.sunshineforce.hardware.domain.Sport;
import com.sunshineforce.hardware.domain.request.HeartRateRequest;
import com.sunshineforce.hardware.domain.request.SportClassRequest;
import com.sunshineforce.hardware.domain.request.SportPersonalRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * ProjectName: sunshineforce-hardware
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/9/19 18:31
 * ModifyUser: bjlixiaopeng
 * Class Description:
 * To change this template use File | Settings | File and Code Template
 */

@Repository
public interface SportMapper extends Mapper<Sport> {
    List<String> getProbeMac(SportClassRequest sportClassRequest);
    Long getSkipNumWithModel(SportClassRequest sportClassRequest);
    Map<String, Object> timeModelBest(SportClassRequest sportClassRequest);
    Map<String, Object> numModelBest(SportClassRequest sportClassRequest);

    Integer getPersonalTotalTime(SportPersonalRequest sportPersonalRequest);
    Long getPersonalTotalNum(SportPersonalRequest sportPersonalRequest);
    Long getSkipGroupNum(SportPersonalRequest sportPersonalRequest);
    List<Integer> getTimeGroup(SportPersonalRequest sportPersonalRequest);
    List<Integer>getNumGroup(SportPersonalRequest sportPersonalRequest);

    Integer getAveHeartRate(HeartRateRequest heartRateRequest);
    Integer getMinHeartRate(HeartRateRequest heartRateRequest);
    Integer getMaxHeartRate(HeartRateRequest heartRateRequest);
    Double getLowTime(HeartRateRequest heartRateRequest);
    Double getMiddleTime(HeartRateRequest heartRateRequest);
    Double getHeightTime(HeartRateRequest heartRateRequest);

    List<Integer> getMemberIdByClassQunId(Integer classQunId);
}
