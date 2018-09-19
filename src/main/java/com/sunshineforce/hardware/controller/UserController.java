package com.sunshineforce.hardware.controller;

import com.sunshineforce.hardware.base.Constants;
import com.sunshineforce.hardware.base.enums.ErrorCode;
import com.sunshineforce.hardware.base.utils.ResponseData;
import com.sunshineforce.hardware.domain.User;
import com.sunshineforce.hardware.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA
 * ProjectName: sunshineforce-hardware
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/9/19 18:30
 * ModifyUser: bjlixiaopeng
 * Class Description:
 * To change this template use File | Settings | File and Code Template
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData login(@RequestBody User loginParams,HttpServletRequest request){
        if (checkParams(loginParams)) {
            return ResponseData.ResultFactory.makeErrorResult(ErrorCode.ILLEGAL_PARAM.getCode(),ErrorCode.ILLEGAL_PARAM.getMessage());
        }
        User user = userService.queryUserByName(loginParams.getUsername(), loginParams.getPassword());
        if (user == null) {
            return ResponseData.ResultFactory.makeErrorResult(ErrorCode.UNAUTHORIZED.getCode(),ErrorCode.UNAUTHORIZED.getMessage());
        }else {
            request.getSession().setAttribute(Constants.LOGIN_USER,user);
        }
        return ResponseData.ResultFactory.makeOKResult();
    }

    @RequestMapping(value = "/loginOut",method = RequestMethod.POST)
    @ResponseBody
    public void loginOut(HttpServletRequest request){
        request.getSession().setAttribute(Constants.LOGIN_USER,null);
    }

    private boolean checkParams(User user){
        if (user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return false;
        }
        return true;
    }
}
