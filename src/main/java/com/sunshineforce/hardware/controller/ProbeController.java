package com.sunshineforce.hardware.controller;

import com.sunshineforce.hardware.base.enums.ErrorCode;
import com.sunshineforce.hardware.base.utils.ResponseData;
import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.domain.Location;
import com.sunshineforce.hardware.domain.Probe;
import com.sunshineforce.hardware.domain.response.BraceletdataResponse;
import com.sunshineforce.hardware.domain.response.LocationResponse;
import com.sunshineforce.hardware.domain.response.ProbeResponse;
import com.sunshineforce.hardware.service.IProbeService;
import com.sunshineforce.hardware.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.Response;
import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("probe")
public class ProbeController {

	@Autowired
	private IProbeService iProbeService;

	@RequestMapping("initList")
	@ResponseBody
	public ResponseData initProbe(){
		ResponseData responseData = ResponseData.ResultFactory.makeOKResult();
		List<String> list = iProbeService.initProbe();
		ProbeResponse probeResponse = new ProbeResponse();
		probeResponse.setProbeMacList(list);
		responseData.setData(probeResponse);
		return responseData;
	}

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

	@RequestMapping("getListLocation")
	@ResponseBody
	public ResponseData getListLocation(@RequestBody Map<String, Object> request, @RequestParam(required = false, defaultValue = "1") int currentPage, @RequestParam(required = false, defaultValue = "10") int pageSize){
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

		List<ProbeResponse> probeResponseList = iProbeService.selectProbesLocation(probe);
		if(probeResponseList.isEmpty()){
			return ResponseData.ResultFactory.makeErrorResult(ErrorCode.NOT_FOUND);
		}
		ProbeResponse probeResponse = new ProbeResponse();
		probeResponse.setRows(probeResponseList);
		probeResponse.setTotal(iProbeService.countProbes(probe));
		responseData.setData(probeResponse);
		return responseData;
	}

	@RequestMapping("getListThroughtout")
	@ResponseBody
	public ResponseData getListThroughtout(@RequestBody Map<String, Object> request, @RequestParam(required = false, defaultValue = "1") int currentPage, @RequestParam(required = false, defaultValue = "10") int pageSize){
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

		List<ProbeResponse> probeResponseList = iProbeService.selectProbesThroughtout(probe);
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

	@RequestMapping("getTime")
	@ResponseBody
	public String getTime(){
		return TimeUtil.getTimeString(new Date(), TimeUtil.dataSecondString);
	}

	@RequestMapping("getProbeThroughtoutListByMac")
	@ResponseBody
	public ResponseData getProbeThroughtoutListById(String probeMac){
		ResponseData responseData = ResponseData.ResultFactory.makeOKResult();
		ProbeResponse probeResponse = iProbeService.getProbeThroughtoutListByMac(probeMac);
		responseData.setData(probeResponse);
		return responseData;
	}

	@RequestMapping("getProbeThroughtoutListByTime")
	@ResponseBody
	public ResponseData getProbeThroughtoutListByTime(String endTime){
		ResponseData responseData = ResponseData.ResultFactory.makeOKResult();
		ProbeResponse probeResponse = iProbeService.getProbeThroughtoutListByTime(endTime);
		responseData.setData(probeResponse);
		return responseData;
	}

	@RequestMapping("getProbeThroughtoutListByTimeWithMac")
	@ResponseBody
	public ResponseData getProbeThroughtoutListByTimeWithMac(String beginTime, String endTime){
		ResponseData responseData = ResponseData.ResultFactory.makeOKResult();
		ProbeResponse probeResponse = iProbeService.getProbeThroughtoutListByTimeWithMac(beginTime, endTime);
		responseData.setData(probeResponse);
		return responseData;
	}

	@RequestMapping("getProbeLocation")
	@ResponseBody
	public ResponseData getProbeLocation(String braceletMac){
		braceletMac = "e5d41c33b7cf";
		ResponseData responseData = ResponseData.ResultFactory.makeOKResult();
		double location = iProbeService.getLocation(braceletMac);
		DecimalFormat format = new DecimalFormat("0.00");
		String l = format.format(location);

		Location location1 = new Location();
		location1.setBraceletMac(braceletMac);
		location1.setLocation(l);
		List<Location> locations = new ArrayList<>();
		LocationResponse locationResponse = new LocationResponse();
		locations.add(location1);
		locationResponse.setRows(locations);
		locationResponse.setTotal(1);
		responseData.setData(locationResponse);
		return responseData;
	}
}
