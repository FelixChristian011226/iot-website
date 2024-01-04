package com.felix.iotbackend.service.impl;

import com.felix.iotbackend.mapper.DeviceDataMapper;
import com.felix.iotbackend.pojo.DeviceData;
import com.felix.iotbackend.service.DeviceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    @Autowired
    private DeviceDataMapper deviceDataMapper;

    @Override
    public void saveToDB(DeviceData deviceData) {
        deviceData.setCreateTime(LocalDateTime.now());
        deviceDataMapper.saveToDB(deviceData);
    }

    @Override
    public List<DeviceData> listByDeviceId(String deviceId) {
        return deviceDataMapper.listByDeviceId(deviceId);
    }

    @Override
    public Integer count() {
        return deviceDataMapper.count();
    }

    @Override
    public List<DeviceData> listAll() {
        return deviceDataMapper.listAll();
    }
}
