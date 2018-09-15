package com.sunshineforce.hardware.controller;

import com.sunshineforce.hardware.base.enums.ErrorCode;
import com.sunshineforce.hardware.base.utils.ResponseData;
import com.sunshineforce.hardware.domain.Probe;
import com.sunshineforce.hardware.domain.response.ProbeResponse;
import com.sunshineforce.hardware.service.IProbeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("probe")
public class ProbeController {

	@Autowired
	private IProbeService iProbeService;

	@RequestMapping("getList")
	@ResponseBody
	public ResponseData getList(@RequestBody Map<String, Object> request, @RequestParam(required = false, defaultValue = "1") int currentPage, @RequestParam(required = false, defaultValue = "10") int pageSize){
		ResponseData responseData = ResponseData.ResultFactory.makeOKResult();
		Probe probe = new Probe();
		probe.setCurrentPage(Integer.parseInt(request.get("currentPage").toString()));
		probe.setPageSize(Integer.parseInt(request.get("pageSize").toString()));
		probe.setOrderName(request.get("orderName").toString());
		probe.setOrderType(request.get("orderType").toString());
		if(request.containsKey("probeMac")){
            probe.setProbeMac(request.get("probeMac").toString());
        }
		if(request.containsKey("location")){
            probe.setLocation(request.get("location").toString());
        }

		List<ProbeResponse> probeResponseList = iProbeService.selectProbes(probe);
		if(probeResponseList.isEmpty()){
			return ResponseData.ResultFactory.makeErrorResult(ErrorCode.NOT_FOUND);
		}
		ProbeResponse probeResponse = new ProbeResponse();
		probeResponse.setRows(probeResponseList);
		probeResponse.setTotal(iProbeService.countProbes(probe));
		responseData.setData(probeResponse);
		return responseData;
	}

    @RequestMapping("addProbe")
    @ResponseBody
	public ResponseData addProbe(Probe probe){
        ResponseData responseData = ResponseData.ResultFactory.makeOKResult();
        int result = iProbeService.addProbe(probe);
        if(result <= 0){
            return ResponseData.ResultFactory.makeErrorResult(ErrorCode.SYSTEM_ERROR);
        }
        return responseData;
    }

    @RequestMapping("deleteProbeByIds")
    @ResponseBody
    public ResponseData deleteProbeByIds(String ids){
        ResponseData responseData = ResponseData.ResultFactory.makeOKResult();
	    ProbeResponse probeResponse = iProbeService.deleteProbeByIds(ids);
        responseData.setData(probeResponse);
        return responseData;
    }
}
