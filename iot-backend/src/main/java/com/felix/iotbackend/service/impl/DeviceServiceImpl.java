package com.felix.iotbackend.service.impl;

import com.felix.iotbackend.mapper.DeviceMapper;
import com.felix.iotbackend.pojo.Device;
import com.felix.iotbackend.service.DeviceService;
import com.felix.iotbackend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public void add(Device device) {
        device.setCreateTime(LocalDateTime.now());
        device.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userID = (Integer) map.get("id");
        device.setCreateUser(userID);
        deviceMapper.add(device);
    }

    @Override
    public void update(Device device) {
        device.setUpdateTime(LocalDateTime.now());
        deviceMapper.update(device);
    }

    @Override
    public void deleteByDeviceId(String deviceId) {
        deviceMapper.deleteByDeviceId(deviceId);
    }

    @Override
    public List<Device> list() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return deviceMapper.list(userId);
    }
}
