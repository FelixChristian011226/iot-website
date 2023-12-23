package com.felix.iotbackend.service;

import com.felix.iotbackend.pojo.User;

public interface UserService {
    User findByUsername(String username);

    void register(String username, String password);

    void update(User u);

}
