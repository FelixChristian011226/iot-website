package com.felix.iotbackend.mapper;

import com.felix.iotbackend.pojo.Device;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeviceMapper {

    @Insert("insert into device(device_id,title,category,create_user,create_time,update_time)" +
            "values(#{deviceId},#{title},#{category},#{createUser},#{createTime},#{updateTime})")
    void add(Device device);

    @Update("update device set title=#{title},category=#{category},update_time=#{updateTime} where device_id=#{deviceId}")
    void update(Device device);

    @Delete("delete from device where device_id=#{deviceId}")
    void deleteByDeviceId(String deviceId);

    @Update("update device set connect=#{connect} where device_id=#{deviceId}")
    void conncect(String deviceId, boolean connect);

    @Select("select * from device where create_user=#{userId}")
    List<Device> list(Integer userId);

    @Select("select connect from device where device_id=#{deviceId}")
    boolean checkConnect(String deviceId);
}
