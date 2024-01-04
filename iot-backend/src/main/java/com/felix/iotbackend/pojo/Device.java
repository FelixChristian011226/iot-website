package com.felix.iotbackend.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Device {
    private Integer id;//主键ID
    private String deviceId;//设备ID
    private String title;//设备名称
    private String category;//设备分类
    private boolean connect;//设备状态 未连接|已连接
    private Integer createUser;//创建人ID
    private String location;//设备位置
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
