package com.felix.iotbackend.controller;

import com.felix.iotbackend.pojo.Result;
import com.felix.iotbackend.pojo.User;
import com.felix.iotbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(String username, String password){
        //FIND USER
        User u = userService.findByUsername(username);
        if(u==null){
            //REGISTER
            userService.register(username,password);
            return Result.success();
        }else{
            return Result.error("用户名已存在");
        }
    }

}
