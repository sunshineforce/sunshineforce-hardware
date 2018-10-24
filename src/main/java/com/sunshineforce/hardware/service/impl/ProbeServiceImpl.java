package com.sunshineforce.hardware.service.impl;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunshineforce.hardware.base.enums.IsNormalCode;
import com.sunshineforce.hardware.base.enums.StatusCode;
import com.sunshineforce.hardware.base.mybatis.impl.BasicSetServiceImpl;
import com.sunshineforce.hardware.dao.mapper.ProbeMapper;
import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.domain.Probe;
import com.sunshineforce.hardware.domain.request.BraceletdataRequest;
import com.sunshineforce.hardware.domain.response.ProbeResponse;
import com.sunshineforce.hardware.service.IBraceletdataService;
import com.sunshineforce.hardware.service.IProbeService;
import com.sunshineforce.hardware.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProbeServiceImpl extends BasicSetServiceImpl<Probe> implements IProbeService {

    @Autowired
    private ProbeMapper probeMapper;

    @Autowired
    private IBraceletdataService iBraceletdataService;

    @Override
    public List<ProbeResponse> selectProbes(Probe probe) {
        List<ProbeResponse>  probeResponseList = new ArrayList<>();
        Example probeExample = buildExample(probe);
        PageHelper.startPage(probe.getCurrentPage(), probe.getPageSize(), probe.getOrderName() + " " + probe.getOrderType());
        List<Probe> probeList = probeMapper.selectByExample(probeExample);
        PageInfo<Probe> probePageInfo = new PageInfo<>(probeList);
        //计算吞吐量，每分钟收到的数据条数
        probePageInfo.getList().stream().forEach(probeObj -> {
            ProbeResponse probeResponse = new ProbeResponse();
            try{
                BeanUtils.copyProperties(probeResponse, probeObj);
            }catch(IllegalAccessException e){
                System.out.println(e);
            }catch(InvocationTargetException e){
                System.out.println(e);
            }

            String probeMac = probeResponse.getProbeMac();
            long currentTime = System.currentTimeMillis();
            //long currentTime = 1536885977836l;
            long beginTime = currentTime - 10 * 1000;
            BraceletdataRequest braceletdataRequest = new BraceletdataRequest(beginTime, currentTime, probeMac);
            //计算10s内收到数据条数
            long regularThroughput = iBraceletdataService.countBraceletdata(braceletdataRequest);
            probeResponse.setRegularThroughput(regularThroughput);
            if(regularThroughput > 0 ){
                if(regularThroughput > 12){
                    probeResponse.setIsNormalStr(IsNormalCode.HEIGHT.getIsMormal());
                }else if(regularThroughput < 8){
                    probeResponse.setIsNormalStr(IsNormalCode.LOW.getIsMormal());
                }else{
                    probeResponse.setIsNormalStr(IsNormalCode.REGULAR.getIsMormal());
                }
            }
            int status = probeResponse.getStatus();
            if(status == StatusCode.NORMAL.getId()){
                probeResponse.setStatusStr(StatusCode.NORMAL.getStatus());
            }else{
                probeResponse.setStatusStr(StatusCode.NOTNORMAL.getStatus());
            }

            probeResponseList.add(probeResponse);
        });
        return probeResponseList;
    }

    @Override
    public long countProbes(Probe probe) {
        Example probeExample = buildExample(probe);
        return probeMapper.selectCountByExample(probeExample);
    }

    @Override
    public int addProbe(Probe probe) {
        probe.setIsNormal(IsNormalCode.REGULAR.getId());
        probe.setRegularThroughput(0l);
        probe.setStatus(StatusCode.NORMAL.getId());
        int result = probeMapper.insert(probe);
        return result;
    }

    @Override
    @Transactional
    public ProbeResponse deleteProbeByIds(String ids) {
        List<String> failList = new ArrayList<>();
        List<String> successList = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            Probe probe = new Probe();
            probe.setId(Integer.parseInt(id));
            probe = probeMapper.selectOne(probe);
            int result = 0;
            try{
                result = probeMapper.delete(probe);
            }catch(RuntimeException e){
               System.out.println(e);
            }

            if(result <= 0){
                failList.add(probe.getProbeMac());
            }else{
                successList.add(probe.getProbeMac());
            }
        });
        ProbeResponse probeResponse = new ProbeResponse();
        probeResponse.setFailList(failList);
        probeResponse.setSuccessList(successList);
        return probeResponse;
    }

    @Override
    public int getProbeByMac(String mac) {
        Probe probe = new Probe();
        probe.setProbeMac(mac);
        return probeMapper.selectCountByExample(buildExample(probe));
    }

//    以1分钟为单位查询吞吐量
    @Override
    public ProbeResponse getProbeThroughtoutListByMac(String probeMac) {
        ProbeResponse probeResponse = new ProbeResponse();
        List<List<String>> result = new ArrayList<>();
        long currentTime = System.currentTimeMillis();
        long regularTime = currentTime;
        while(regularTime >= currentTime - 1000 * 3600 * 24){
            long beforeTime = regularTime - 1000 * 60;
            BraceletdataRequest braceletdataRequest = new BraceletdataRequest(beforeTime, regularTime, probeMac);
            //计算1min内收到数据条数
            long regularThroughput = iBraceletdataService.countBraceletdata(braceletdataRequest);
            List<String> throughoutList = new ArrayList<>();
            throughoutList.add(TimeUtil.getTimeString(new Date(regularTime), TimeUtil.dataSecondString));
            throughoutList.add(regularThroughput + "");
            result.add(throughoutList);
            regularTime = beforeTime;
        }
        Collections.reverse(result);
        regularTime = currentTime - 3600 * 1000;
        probeResponse.setThroughoutList(result);
        probeResponse.setRegularTime(TimeUtil.getTimeString(new Date(regularTime), TimeUtil.dataSecondString));
        return probeResponse;
    }

    @Override
    public ProbeResponse getProbeThroughtoutListByTime(String endTime){
        Probe probe = new Probe();
        List<String> probeMacList = new ArrayList<>();
        List<Map<String, Object>> probeValueList = new ArrayList<>();
        Example probeExample = buildExample(probe);
        List<Probe> probeList = probeMapper.selectByExample(probeExample);
        probeList.stream().forEach(probeObj -> {
            try{
                long beginTime = Long.parseLong(endTime) - 60 * 1000;
                BraceletdataRequest braceletdataRequest = new BraceletdataRequest(beginTime, Long.parseLong(endTime), probeObj.getProbeMac());
                //计算1分钟内收到数据条数
                long regularThroughput = iBraceletdataService.countBraceletdata(braceletdataRequest);
                probeMacList.add(probeObj.getProbeMac());
                Map<String, Object> map = new HashMap<>();
                map.put("value", regularThroughput);
                map.put("name", probeObj.getProbeMac());
                probeValueList.add(map);
            }catch(Exception e){
                log.info(e.getMessage());
            }
        });
        ProbeResponse probeResponse = new ProbeResponse();
        probeResponse.setProbeMacList(probeMacList);
        probeResponse.setProbeValueList(probeValueList);
        probeResponse.setRegularTime(TimeUtil.getTimeString(new Date(Long.parseLong(endTime)), TimeUtil.dataMinuteString));
        return probeResponse;
    }

    @Override
    public ProbeResponse getProbeThroughtoutListByTimeWithMac(String beginTime, String endTime) {
        Probe probe = new Probe();
        List<String> probeMacList = new ArrayList<>();
        List<Long> probeThroughtoutList = new ArrayList<>();
        Example probeExample = buildExample(probe);
        List<Probe> probeList = probeMapper.selectByExample(probeExample);
        probeList.stream().forEach(probeObj -> {
            try{
                BraceletdataRequest braceletdataRequest = new BraceletdataRequest(Long.parseLong(beginTime), Long.parseLong(endTime), probeObj.getProbeMac());
                //计算收到数据条数
                long regularThroughput = iBraceletdataService.countBraceletdata(braceletdataRequest);
                probeMacList.add(probeObj.getProbeMac());
                probeThroughtoutList.add(regularThroughput);
            }catch(Exception e){
                log.info(e.getMessage());
            }
        });
        ProbeResponse probeResponse = new ProbeResponse();
        probeResponse.setProbeMacList(probeMacList);
        probeResponse.setProbeThroughtoutList(probeThroughtoutList);
        return probeResponse;
    }

    @Override
    public List<String> initProbe() {
        Probe probe = new Probe();
        probe.setStatus(StatusCode.NORMAL.getId());
        Example probeExample = buildExample(probe);
        List<Probe> probeList = probeMapper.selectByExample(probeExample);
        List<String> list = probeList.stream().map(obj -> obj.getProbeMac()).collect(Collectors.toList());
        return list;
    }

    @Override
    public double getLocation(String braceletMac) {
        long endTime = System.currentTimeMillis();
        long beginTime = endTime - 60 * 1000;
        BraceletdataRequest braceletdataRequest = new BraceletdataRequest(beginTime, endTime);
        braceletdataRequest.setBraceletMac(braceletMac);
        //计算1分钟内收到数据条数
        double result = 0;
        List<Braceletdata> braceletdataList = iBraceletdataService.getBraceletdatasList(braceletdataRequest);
        for(Braceletdata braceletdata : braceletdataList){
            int signal = Math.abs(braceletdata.getSignalValue() != null ? braceletdata.getSignalValue() : 0);
            double power = (signal-59)/(10*2.0);
            double distance = Math.pow(10, power);
            result = result + distance;
        }
        return result/braceletdataList.size();
    }

    private Example buildExample(Probe probe){
        Example probeExample = new Example(Probe.class);
        Example.Criteria criteria = probeExample.createCriteria();
        String orderName = Optional.ofNullable(probe.getOrderName()).orElse("id");
        String orderType = Optional.ofNullable(probe.getOrderType()).orElse("asc");
        probeExample.setOrderByClause(orderName + " " + orderType);
        String location = Optional.ofNullable(probe.getLocation()).orElse(null);
        if(location != null){
            criteria.andLike("location", "%" + location + "%");
        }
        int status = Optional.ofNullable(probe.getStatus()).orElse(-1);
        if(status != -1){
            criteria.andEqualTo("status", status);
        }
        String probeMac = Optional.ofNullable(probe.getProbeMac()).orElse(null);
        if(!StringUtils.isEmpty(probeMac)){
            criteria.andEqualTo("probeMac", probeMac);
        }
        return probeExample;
    }
}
