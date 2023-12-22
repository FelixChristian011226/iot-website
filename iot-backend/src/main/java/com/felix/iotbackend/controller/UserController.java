package com.felix.iotbackend.controller;

import com.felix.iotbackend.pojo.Result;
import com.felix.iotbackend.pojo.User;
import com.felix.iotbackend.service.UserService;
import com.felix.iotbackend.utils.JwtUtil;
import com.felix.iotbackend.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
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

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        //FIND USER
        User u = userService.findByUsername(username);
        //JUDGE EXISTENCE
        if(u==null){
            return Result.error("用户名不存在");
        }
        //JUDGE PASSWORD(ENCRYPTED)
        if(Md5Util.getMD5String(password).equals(u.getPassword())) {
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",u.getId());
            claims.put("username",u.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

}
