package com.felix.iotbackend.service;

import com.felix.iotbackend.pojo.DeviceData;

import java.util.List;

public interface DeviceDataService {
    void saveToDB(DeviceData deviceData);

    List<DeviceData> listByDeviceId(String deviceId);

    Integer count();

    List<DeviceData> listAll();
}
