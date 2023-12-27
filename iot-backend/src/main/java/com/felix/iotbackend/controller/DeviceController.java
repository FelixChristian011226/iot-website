package com.felix.iotbackend.controller;

import com.felix.iotbackend.pojo.Device;
import com.felix.iotbackend.pojo.Result;
import com.felix.iotbackend.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public Result add(@RequestBody Device device){
        deviceService.add(device);
        return Result.success();
    }

    @GetMapping
    public Result<List<Device>> list(){
        List<Device> deviceList = deviceService.list();
        return Result.success(deviceList);
    }
}
