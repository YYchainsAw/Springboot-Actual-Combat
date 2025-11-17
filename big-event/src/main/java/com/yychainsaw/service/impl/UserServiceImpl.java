package com.yychainsaw.service.impl;

import com.yychainsaw.mapper.UserMapper;
import com.yychainsaw.pojo.User;
import com.yychainsaw.service.UserService;
import com.yychainsaw.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //密码加密
        String md5String = Md5Util.getMD5String(password);

        userMapper.add(username, md5String);
    }
}
