package com.felix.iotbackend.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeviceData {
    private Integer id;//主键ID
    private Integer alert;//告警信息
    private String clientId;//设备ID
    private String info;//数据信息
    private double lat;//纬度
    private double lng;//经度
    private LocalDateTime createTime;//创建时间
    private Long timestamp;//时间戳
    private Integer value;//数据值
}
