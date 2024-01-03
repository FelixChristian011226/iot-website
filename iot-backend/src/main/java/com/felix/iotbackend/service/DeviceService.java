package com.felix.iotbackend.service;

import com.felix.iotbackend.pojo.Device;

import java.util.List;

public interface DeviceService {
    void add(Device device);

    void update(Device device);

    void deleteByDeviceId(String deviceId);

    List<Device> list();

}
