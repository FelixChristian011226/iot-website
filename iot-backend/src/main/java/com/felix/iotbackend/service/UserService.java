package com.felix.iotbackend.service;

import com.felix.iotbackend.pojo.User;

public interface UserService {
    User findByUsername(String username);

    User findByEmail(String email);

    void register(String username, String password);

    void update(User u);

    void updateAvatar(String avatarUrl);

    void updatePassword(String newPassword);

}
