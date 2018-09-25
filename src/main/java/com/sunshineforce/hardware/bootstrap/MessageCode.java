package com.sunshineforce.hardware.bootstrap;

public enum MessageCode {

    REQUURE_LOCATION_SERVER(179),
    PROBE_OPEN_WIFI(181),
    SET_WIFI_SSIDPWD(182),
    SET_REPLY_TYPE(180),
    HEARTBEAT_REQUIRE(183),
    HEARTBEAT_REPLY(184);
    private int code;

    MessageCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
