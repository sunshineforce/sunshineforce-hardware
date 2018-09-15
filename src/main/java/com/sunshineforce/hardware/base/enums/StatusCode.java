package com.sunshineforce.hardware.base.enums;

public enum StatusCode {

    OPEN(0, "open"),
    CLOSE(1, "close");

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
