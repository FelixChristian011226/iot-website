package com.felix.iotbackend.service.impl;

import com.felix.iotbackend.mapper.UserMapper;
import com.felix.iotbackend.pojo.User;
import com.felix.iotbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }
}
