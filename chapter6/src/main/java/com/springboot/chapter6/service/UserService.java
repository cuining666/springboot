package com.springboot.chapter6.service;

import com.springboot.chapter6.pojo.User;

import java.util.List;

public interface UserService {
    User getUser(Long id);

    User insertUser(User user);

    User updateByUserName(Long id, String userName);

    List<User> findUsers(String userName, String note);

    int deleteUser(Long id);
}
