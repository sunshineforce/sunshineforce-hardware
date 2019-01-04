package com.sunshineforce.hardware.controller;

import com.sunshineforce.hardware.base.enums.ErrorCode;
import com.sunshineforce.hardware.base.utils.ResponseData;
import com.sunshineforce.hardware.dao.mapper.BraceletdataMapper;
import com.sunshineforce.hardware.dao.mapper.SportIntensityMapper;
import com.sunshineforce.hardware.domain.Braceletdata;
import com.sunshineforce.hardware.domain.Probe;
import com.sunshineforce.hardware.domain.SportIntensity;
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
import java.util.Random;

@Controller
@RequestMapping("bracelet")
public class BraceletController {

	@Autowired
	private IBraceletdataService iBraceletdataService;
	@Autowired
	private BraceletdataMapper braceletdataMapper;

	@Autowired
	private SportIntensityMapper sportIntensityMapper;

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
	public void importData() throws InterruptedException{
		Braceletdata list = new Braceletdata();
		List<Braceletdata> braceletdataList = iBraceletdataService.selectList();
		Long time = 15174269010l;
		for(Braceletdata braceletdata : braceletdataList){
			braceletdata.setAddTime(System.currentTimeMillis() - time);
			braceletdata.setUtc(System.currentTimeMillis() - time);
			braceletdata.setProbeMac("ed5160faef42");
			braceletdata.setBraceletMac("cfebf6a80bbd");
			if(braceletdata.getHeartBeat() == 0){
				int max = 110;
				int min = 60;
				Random random = new Random();
				int s = random.nextInt(max) % (max - min + 1) + min;
				braceletdata.setHeartRate(s);
			}
			int max = 2;
			int min = 0;
			Random random = new Random();
			int s = random.nextInt(max) % (max - min + 1) + min;
			braceletdata.setSkipModel(s);

			int tmax = 2;
			int tmin = 0;
			Random trandom = new Random();
			int ts = trandom.nextInt(tmax) % (tmax - tmin + 1) + tmin;
			braceletdata.setSkipTime(ts);

			int nmax = 100;
			int nmin = 50;
			Random nrandom = new Random();
			int ns = nrandom.nextInt(nmax) % (nmax - nmin + 1) + nmin;
			braceletdata.setSkipNum(ns);
			braceletdataMapper.replaceInsert(braceletdata);
			System.out.println("----------------");
			Thread.sleep(1000);
		}
	}

	@RequestMapping("importData2")
	@ResponseBody
	public void importData2(){
		SportIntensity sportIntensity = new SportIntensity();
		Long time = 15174943015l;
		for(int i = 0; i < 7200*12; i = i + 7200){
			int max = 30;
			int min = 5;
			Random random = new Random();
			int s = random.nextInt(max) % (max - min + 1) + min;
			long begin_time = 1526515800 + i;
			long end_time = begin_time + 60*s;
			sportIntensity.setBeginTime(begin_time);
			sportIntensity.setEndTime(end_time);


			int lmax = 65;
			int lmin = 55;
			int ls = random.nextInt(lmax) % (lmax - lmin + 1) + lmin;
			sportIntensity.setMinHr(ls);

			int hmax = 120;
			int hmin = 100;
			int hs = random.nextInt(hmax) % (hmax - hmin + 1) + hmin;
			sportIntensity.setMaxHr(hs);

			int amax = 75;
			int amin = 90;
			int as = random.nextInt(amax) % (amax - amin + 1) + amin;
			sportIntensity.setAvgHr(as);

			int rmax = 70;
			int rmin = 60;
			int rs = random.nextInt(rmax) % (rmax - rmin + 1) + rmin;
			sportIntensity.setHrrest(rs);

			int ldmax = 120;
			int ldmin = 60;
			int lds = random.nextInt(ldmax) % (ldmax - ldmin + 1) + ldmin;
			sportIntensity.setLowDuration(lds);

			int mdmax = 120;
			int mdmin = 60;
			int mds = random.nextInt(mdmax) % (mdmax - mdmin + 1) + mdmin;
			sportIntensity.setHighDuration(mds);

			long hdmax = end_time-begin_time-mdmax-ldmax-60;
			sportIntensity.setMidDuration(hdmax);

			sportIntensity.setStudentId(90);
			sportIntensity.setGradeId(9999);
			sportIntensity.setSchoolId(3);
			sportIntensityMapper.insert(sportIntensity);
		}
	}
}
