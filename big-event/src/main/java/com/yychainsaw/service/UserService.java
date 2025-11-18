package com.yychainsaw.service;

import com.yychainsaw.pojo.User;
import jakarta.validation.constraints.Pattern;

public interface UserService {
    void register(String username, String password);

    User findByUsername(String username);

    void update(User user);

    void updateAvatar(String avatarUrl);
}
