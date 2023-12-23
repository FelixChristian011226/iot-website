package com.felix.iotbackend.controller;

import com.felix.iotbackend.pojo.Result;
import com.felix.iotbackend.pojo.User;
import com.felix.iotbackend.service.UserService;
import com.felix.iotbackend.utils.JwtUtil;
import com.felix.iotbackend.utils.Md5Util;
import com.felix.iotbackend.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
            //STORE TOKEN INTO REDIS
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.DAYS);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User u = userService.findByUsername(username);
        return Result.success(u);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User u){
        userService.update(u);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String,String> params, @RequestHeader("Authorization") String token){
        //VALIDATE PARAMS
        String oldPassword = params.get("old_password");
        String newPassword = params.get("new_password");
        String cfmPassword = params.get("cfm_password");
        if(!StringUtils.hasLength(oldPassword) || !StringUtils.hasLength(newPassword) || !StringUtils.hasLength(cfmPassword)){
            return Result.error("参数不完整");
        }
        //VALIDATE OLD PASSWORD
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User u = userService.findByUsername(username);
        if(!u.getPassword().equals(Md5Util.getMD5String(oldPassword))){
            return Result.error("原密码错误");
        }
        //VALIDATE NEW PASSWORD
        if(!newPassword.equals(cfmPassword)){
            return Result.error("两次密码不一致");
        }
        //UPDATE PASSWORD
        userService.updatePassword(newPassword);
        //DELETE TOKEN FROM REDIS
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }

}
