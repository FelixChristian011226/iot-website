package com.felix.iotbackend.mapper;

import com.felix.iotbackend.pojo.Device;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeviceMapper {

    @Insert("insert into device(title,category,create_user,create_time,update_time)" +
            "values(#{title},#{category},#{createUser},#{createTime},#{updateTime})")
    void add(Device device);

    @Select("select * from device where create_user=#{userId}")
    List<Device> list(Integer userId);

}
