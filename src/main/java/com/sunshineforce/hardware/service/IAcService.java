package com.sunshineforce.hardware.service;

import com.sunshineforce.hardware.domain.ProbeInfo;
import com.sunshineforce.hardware.domain.Wifi;

public interface IAcService {
    int updateConfig(Wifi wifi);
    int toggleWifi(Wifi wifi);
    int setServer(Wifi wifi);
    ProbeInfo requestSelect(Wifi wifi);
    int restart(Wifi wifi);
}
