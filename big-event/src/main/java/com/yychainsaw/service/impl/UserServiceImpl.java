package com.yychainsaw.service.impl;

import com.yychainsaw.mapper.UserMapper;
import com.yychainsaw.pojo.User;
import com.yychainsaw.service.UserService;
import com.yychainsaw.utils.Md5Util;
import com.yychainsaw.utils.ThreadLocalUtil;
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
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, id);

    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }

    @Override
    public void register(String username, String password) {
        //密码加密
        String md5String = Md5Util.getMD5String(password);

        userMapper.add(username, md5String);
    }
}
