package com.sunshineforce.hardware.base.enums;

public enum StatusCode {

    NORMAL(0, "normal"),
    NOTNORMAL(1, "notNormal");
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
