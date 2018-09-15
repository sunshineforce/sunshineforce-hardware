package com.sunshineforce.hardware.base.enums;

public enum IsNormalCode {
    NORMAL(0, "normal"),
    NOTNORMAL(1, "notNormal");

    private int id;
    private String isMormal;

    IsNormalCode(int id, String isMormal) {
        this.id = id;
        this.isMormal = isMormal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsMormal() {
        return isMormal;
    }

    public void setIsMormal(String isMormal) {
        this.isMormal = isMormal;
    }
}
