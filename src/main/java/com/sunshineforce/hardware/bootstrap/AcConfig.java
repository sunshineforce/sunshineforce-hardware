package com.sunshineforce.hardware.bootstrap;

import com.sunshineforce.hardware.domain.Wifi;
import com.sunshineforce.hardware.util.ByteUtil;
import com.sunshineforce.hardware.util.UdpUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.UnknownHostException;

@Slf4j
public class AcConfig {

    private static final String HEADER = "ad";
    private static final String PD = "01";
    private static final String CONFIGTYPE = "b6";

    private static final String TOGGLEWIFITYPE = "b5";

    private static final String SERVERTYPE = "b3";

    private static final String REQUESTSELECT = "bc";

    private static final String RESTARTTYPE = "bb";

    private static final String RESPONSETYPE = "b8";


    public static byte[] getHeartBeatResponse(String probeMac){
        StringBuilder builder = new StringBuilder();
        String message = builder.append(HEADER).append(PD).append(RESPONSETYPE).append("0006").append(UdpUtil.macTpHex(probeMac)).toString();
        log.info("heartbeatresponse====="+message.toLowerCase());
        return ByteUtil.HexToByte(message.toLowerCase());
    }

    public static byte[] getRestartMsg(Wifi wifi){
        StringBuilder builder = new StringBuilder();
        String message = builder.append(HEADER).append(PD).append(RESTARTTYPE).append("0006").append(UdpUtil.macTpHex(wifi.getProbeMac())).toString();
        log.info("restart====="+message.toLowerCase());
        return ByteUtil.HexToByte(message.toLowerCase());
    }

    public static byte[] requestSelect(Wifi wifi){
        StringBuilder builder = new StringBuilder();
        String message = builder.append(HEADER).append(PD).append(REQUESTSELECT).append("0006").append(UdpUtil.macTpHex(wifi.getProbeMac())).toString();
        log.info("heartbeatresponse====="+message.toLowerCase());
        return ByteUtil.HexToByte(message.toLowerCase());
    }

    public static byte[] getConfigMsg(Wifi wifi){
        String probeMac = wifi.getProbeMac();
        String ssid = wifi.getSsid();
        String password = wifi.getPassword();
        StringBuilder builder = new StringBuilder();
        String ssidHex = ByteUtil.strToHex(ssid);
        String passwordHex = ByteUtil.strToHex(password);
        log.info("---0----"+ssidHex);
        log.info("---0----"+passwordHex);
        Integer length = 8 + ssidHex.length() / 2 + passwordHex.length() / 2;
        String lengthHex = Integer.toHexString(length);
        while(lengthHex.length() < 4){
            lengthHex = "0" + lengthHex;
        }

        String ssidLengthHex = Integer.toHexString(ssidHex.length() / 2);
        while(ssidLengthHex.length() < 2){
            ssidLengthHex = "0" + ssidLengthHex;
        }

        String passwordLengthHex = Integer.toHexString(passwordHex.length() / 2);
        while(passwordLengthHex.length() < 2){
            passwordLengthHex = "0" + passwordLengthHex;
        }

        builder.append(HEADER).append(PD).append(CONFIGTYPE).append(lengthHex).append(UdpUtil.macTpHex(probeMac)).append(ssidLengthHex).append(ssidHex).append(passwordLengthHex).append(passwordHex);
        log.info("====="+builder.toString());
        return ByteUtil.HexToByte(builder.toString());
    }
    public static byte[] getToggleWifiMsg(Wifi wifi){
        String probeMac = wifi.getProbeMac();
        Integer status = wifi.getStatus();
        String statusHex = "";
        if(status == 1){
            statusHex = "01";  //打开
        }
        if(status == 0){
            statusHex = "00";  //关闭
        }
        StringBuilder builder = new StringBuilder();
        builder.append(HEADER).append(PD).append(TOGGLEWIFITYPE).append("000").append(Integer.toHexString(7)).append(UdpUtil.macTpHex(probeMac)).append(statusHex);
        log.info("toggle----"+builder.toString().toLowerCase());
        return ByteUtil.HexToByte(builder.toString().toLowerCase());
    }

    public static byte[] setServerConfig(Wifi wifi){
        int port = wifi.getPort();
        String ip = wifi.getIp();
        String probeMac = wifi.getProbeMac();
        StringBuilder builder = new StringBuilder();
        String portHex = Integer.toHexString(port);
        while(portHex.length() < 4){
            portHex = "0" + portHex;
        }
        builder.append(HEADER).append(PD).append(SERVERTYPE).append("000c").append(UdpUtil.macTpHex(probeMac)).append(UdpUtil.ipTpHex(ip)).append(portHex);
        log.info("======="+Integer.toHexString(3333));
        log.info("toggle----"+builder.toString().toLowerCase());
        return ByteUtil.HexToByte(builder.toString().toLowerCase());
    }
}
