package com.sunshineforce.hardware.base.enums;

/**
 * Created with IntelliJ IDEA
 * ProjectName: ssm-maven
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/6/27 18:15
 * ModifyUser: bjlixiaopeng
 * Class Description: common paging class
 * To change this template use File | Settings | File and Code Template
 */

public enum ErrorCode {

    SUCCESS(200,"success"),

    DEFAULT_ERROR(40000, "Bad request"),
    PROBLEMS_PARSING_JSON(40001, "Problems parsing JSON"),
    UNSUPPORTED_MEDIA_TYPE(40002, "Unsupported Media Type"),

    UNAUTHORIZED(403, "Unauthorized "),


    NOT_FOUND(40400, "Resource not found"),
    NOT_FOUND_EXPANDPLAN(40401, "No such expandPlan"),
    NOT_FOUND_ADPLAN(40402, "No such adPlan"),
    NOT_FOUND_ADVERTISER(40403, "No such advertiser"),
    NOT_FOUND_ADXTYPE(40404, "No such adxtype"),

    MISSING_FIELD(42200, "Unprocessable entity"),
    ILLEGAL_PARAM(42201, "Illegal parameters"),
    ILLEGAL_DATE_PARAM(42202, "Illegal date parameters"),
    DUPLI_PLANNAME(42203, "PlanName duplicate"),
    MULTIPLE_ID(42204, "Update planName should have only one id"),
    FILE_TYPE_NOT_SUPPORT(42205, "File Type Not Support"),
    FILE_CONTENT_NULL(42205, "File Content Null"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
