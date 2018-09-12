package com.sunshineforce.hardware.service.impl;

import com.sunshineforce.hardware.base.mybatis.impl.BasicSetServiceImpl;
import com.sunshineforce.hardware.domain.Bluetooth;
import com.sunshineforce.hardware.service.IBluetoothService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BluetoothServiceImpl extends BasicSetServiceImpl<Bluetooth> implements IBluetoothService {
    @Override
    public List<Bluetooth> selectUsers() {
        return null;
    }
}
