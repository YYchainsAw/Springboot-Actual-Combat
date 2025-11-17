package com.yychainsaw.service;

import com.yychainsaw.pojo.User;

public interface UserService {
    void register(String username, String password);

    User findByUsername(String username);
}
