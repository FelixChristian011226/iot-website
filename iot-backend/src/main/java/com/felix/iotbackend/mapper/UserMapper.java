package com.felix.iotbackend.mapper;

import com.felix.iotbackend.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * from user where id = #{id}")
    public User findById(Integer id);

}
