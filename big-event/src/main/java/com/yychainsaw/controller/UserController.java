package com.yychainsaw.controller;

import com.yychainsaw.pojo.Result;
import com.yychainsaw.pojo.User;
import com.yychainsaw.service.UserService;
import com.yychainsaw.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询数据库是否存在该用户
        User u = userService.findByUsername(username);

        if (u != null) {
            //存在，返回错误提示
            return Result.error("用户名已存在");
        }else {
            userService.register(username, password);
            return Result.success();
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password) {
        User loginUser = userService.findByUsername(username);

        if (loginUser == null) {
            return Result.error("用户名不存在");
        }

        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            return Result.success("jwt-token-example");
        }

        return Result.error("密码错误");
    }
}
