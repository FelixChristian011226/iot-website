package com.felix.iotbackend.controller;

import com.felix.iotbackend.pojo.DeviceData;
import com.felix.iotbackend.pojo.Result;
import com.felix.iotbackend.service.DeviceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/devicedata")
public class DeviceDataController {

    @Autowired
    private DeviceDataService deviceDataService;

    @GetMapping
    public Result<List<DeviceData>> list(String deviceId){
        List<DeviceData> deviceDataList = deviceDataService.listByDeviceId(deviceId);
        return Result.success(deviceDataList);
    }
}
