package com.sunshineforce.hardware.controller;

import com.sunshineforce.hardware.base.enums.ErrorCode;
import com.sunshineforce.hardware.base.utils.ResponseData;
import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.domain.Probe;
import com.sunshineforce.hardware.domain.request.BraceletdataRequest;
import com.sunshineforce.hardware.domain.response.BraceletdataResponse;
import com.sunshineforce.hardware.domain.response.ProbeResponse;
import com.sunshineforce.hardware.service.IBraceletdataService;
import com.sunshineforce.hardware.service.IProbeService;
import com.sunshineforce.hardware.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("bracelet")
public class BraceletController {

	@Autowired
	private IBraceletdataService iBraceletdataService;


	@RequestMapping("getList")
	@ResponseBody
	public ResponseData getList(@RequestBody Map<String, Object> request, @RequestParam(required = false, defaultValue = "1") int currentPage, @RequestParam(required = false, defaultValue = "10") int pageSize){
		ResponseData responseData = ResponseData.ResultFactory.makeOKResult();
		BraceletdataRequest braceletdata = new BraceletdataRequest();
		braceletdata.setCurrentPage(Integer.parseInt(request.get("currentPage").toString()));
		braceletdata.setPageSize(Integer.parseInt(request.get("pageSize").toString()));
		braceletdata.setOrderName(request.get("orderName").toString());
		braceletdata.setOrderType(request.get("orderType").toString());
		if(request.containsKey("probeMac")){
			braceletdata.setProbeMac(request.get("probeMac").toString());
        }
		if(request.containsKey("braceletMac")){
			braceletdata.setBraceletMac(request.get("braceletMac").toString());
        }
		if(request.containsKey("uuid")){
			braceletdata.setUuid(request.get("uuid").toString());
		}
		if(request.containsKey("skipModel")){
			braceletdata.setSkipModel(Integer.parseInt(request.get("skipModel").toString()));
		}

		List<Braceletdata> braceletdataList = iBraceletdataService.selectBraceletdatas(braceletdata);
		if(braceletdataList.isEmpty()){
			return ResponseData.ResultFactory.makeErrorResult(ErrorCode.NOT_FOUND);
		}
		BraceletdataResponse braceletdataResponse = new BraceletdataResponse();
		braceletdataResponse.setRows(braceletdataList);
		braceletdataResponse.setTotal(iBraceletdataService.countBraceletdata(braceletdata));
		responseData.setData(braceletdataResponse);
		return responseData;
	}

	@RequestMapping("importData")
	@ResponseBody
	public void importData(){

	}
}
