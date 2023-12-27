package com.felix.iotbackend.service;

import com.felix.iotbackend.pojo.Device;

import java.util.List;

public interface DeviceService {
    void add(Device device);

    List<Device> list();

}
