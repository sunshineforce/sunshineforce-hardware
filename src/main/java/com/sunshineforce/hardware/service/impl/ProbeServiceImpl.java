package com.sunshineforce.hardware.service.impl;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.sunshineforce.hardware.base.enums.IsNormalCode;
import com.sunshineforce.hardware.base.enums.StatusCode;
import com.sunshineforce.hardware.base.mybatis.impl.BasicSetServiceImpl;
import com.sunshineforce.hardware.dao.mapper.ProbeMapper;
import com.sunshineforce.hardware.domain.Probe;
import com.sunshineforce.hardware.domain.request.BraceletdataRequest;
import com.sunshineforce.hardware.domain.response.ProbeResponse;
import com.sunshineforce.hardware.service.IBraceletdataService;
import com.sunshineforce.hardware.service.IProbeService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
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
        //计算吞吐量，每分钟收到的数据条数
        probeList.stream().forEach(probeObj -> {
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
            //计算1s内收到数据条数
            long regularThroughput = iBraceletdataService.countBraceletdata(braceletdataRequest);
            probeResponse.setRegularThroughput(regularThroughput);
            if(regularThroughput > 0 ){
                if(regularThroughput > 100){
                    probeResponse.setIsNormalStr(IsNormalCode.HEIGHT.getIsMormal());
                }else if(regularThroughput < 100){
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

    private Example buildExample(Probe probe){
        Example probeExample = new Example(Probe.class);
        Example.Criteria criteria = probeExample.createCriteria();
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
