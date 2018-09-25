package com.sunshineforce.hardware.base.enums;

public enum StatusCode {

    NORMAL(0, "开启"),
    NOTNORMAL(1, "关闭");
    private int id;
    private String status;

    StatusCode(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
