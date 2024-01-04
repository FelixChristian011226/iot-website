package com.felix.iotbackend.service;

import com.felix.iotbackend.pojo.Device;

import java.util.List;

public interface DeviceService {
    void add(Device device);

    void update(Device device);

    void deleteByDeviceId(String deviceId);

    void conncect(String deviceId, boolean connect);

    List<Device> list();

    boolean checkConnect(String deviceId);
}
