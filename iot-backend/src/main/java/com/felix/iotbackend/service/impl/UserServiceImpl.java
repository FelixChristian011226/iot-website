package com.felix.iotbackend.service.impl;

import com.felix.iotbackend.mapper.UserMapper;
import com.felix.iotbackend.pojo.User;
import com.felix.iotbackend.service.UserService;
import com.felix.iotbackend.utils.Md5Util;
import com.felix.iotbackend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

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
    public User findByEmail(String email) {
        User u = userMapper.findByEmail(email);
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
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.update(u);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePassword(String newPassword) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePassword(Md5Util.getMD5String(newPassword),id);
    }

}
