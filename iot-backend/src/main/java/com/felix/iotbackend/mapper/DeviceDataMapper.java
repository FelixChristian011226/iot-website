package com.felix.iotbackend.mapper;

import com.felix.iotbackend.pojo.DeviceData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeviceDataMapper {

    @Insert("insert into device_data(alert,client_id,info,lat,lng,create_time,timestamp,value)" +
            "values(#{alert},#{clientId},#{info},#{lat},#{lng},#{createTime},#{timestamp},#{value})")
    void saveToDB(DeviceData deviceData);

    @Select("select * from device_data where client_id=#{deviceId}")
    List<DeviceData> listByDeviceId(String deviceId);

    @Select("select count(*) from device_data")
    Integer count();

    @Select("select * from device_data")
    List<DeviceData> listAll();
}
