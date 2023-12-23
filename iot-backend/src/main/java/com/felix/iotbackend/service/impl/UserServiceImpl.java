package com.felix.iotbackend.service.impl;

import com.felix.iotbackend.mapper.UserMapper;
import com.felix.iotbackend.pojo.User;
import com.felix.iotbackend.service.UserService;
import com.felix.iotbackend.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //ENCRYPT PASSWORD
        String md5String = Md5Util.getMD5String(password);
        //INSERT
        userMapper.add(username,md5String);
    }

    @Override
    public void update(User u) {
        u.setUpdateTime(LocalDateTime.now());
        userMapper.update(u);
    }

}
