package com.yychainsaw.controller;

import com.yychainsaw.pojo.Result;
import com.yychainsaw.pojo.User;
import com.yychainsaw.service.UserService;
import com.yychainsaw.utils.JwtUtil;
import com.yychainsaw.utils.Md5Util;
import com.yychainsaw.utils.ThreadLocalUtil;
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
    private StringRedisTemplate stringRedisTemplate;

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
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());

            String token = JwtUtil.genToken(claims);

            //存储到Redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

            operations.set(token, token, 1, TimeUnit.HOURS);

            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userinfo(/*@RequestHeader(name = "Authorization") String token*/) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findByUsername(username);

        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody  Map<String, String> params,@RequestHeader("Authorization") String token) {
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasText(oldPwd) || !StringUtils.hasText(newPwd) || !StringUtils.hasText(rePwd)) {
            return Result.error("缺少必要的参数");
        }

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User loginUser = userService.findByUsername(username);

        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码错误");
        }

        if (oldPwd.equals(newPwd)) {
            return Result.error("新密码不能与原密码相同");
        }

        if (!newPwd.matches("^\\S{5,16}$")) {
            return Result.error("新密码长度应在5-16位之间");
        }

        if (!newPwd.equals(rePwd)) {
            return Result.error("两次输入的新密码不一致");
        }
        userService.updatePwd(newPwd);

        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        operations.getOperations().delete(token);

        return Result.success();
    }
}
