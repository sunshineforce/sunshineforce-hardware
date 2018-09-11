package com.sunshineforce.hardware.base.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sunshineforce.hardware.base.enums.ErrorCode;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * ProjectName: ssm-maven
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/6/27 18:18
 * ModifyUser: bjlixiaopeng
 * Class Description: common paging class
 * To change this template use File | Settings | File and Code Template
 */

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"respCode", "count", "data"})
public class ResponseData implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private Object data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static class ResultFactory {

        public static ResponseData makeErrorResult(int errorcode, String msg) {
            ResponseData result = new ResponseData();
            result.setCode(errorcode);
            result.setMsg(msg);
            result.setData("");
            return result;
        }

        public static ResponseData makeErrorResult(ErrorCode errorCode) {
            ResponseData result = new ResponseData();
            result.setCode(errorCode.getCode());
            result.setMsg(errorCode.getMessage());
            result.setData("");
            return result;
        }


        public static ResponseData makeOKResult() {
            ResponseData result = new ResponseData();
            result.setCode(ErrorCode.SUCCESS.getCode());
            return result;
        }

        public static ResponseData makeOKResult(Object object) {
            ResponseData result = new ResponseData();
            result.setCode(ErrorCode.SUCCESS.getCode());
            result.setData(object);
            return result;
        }

        public static ResponseData makeNoPrivilegeResult(int errorcode, String msg) {
            ResponseData result = new ResponseData();
            result.setCode(errorcode);
            result.setMsg(msg);
            result.setData(msg);
            return result;
        }
    }

}
