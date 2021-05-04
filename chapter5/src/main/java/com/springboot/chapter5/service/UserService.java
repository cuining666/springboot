package com.springboot.chapter5.service;

import com.springboot.chapter5.pojo.User;

import java.util.List;

public interface UserService {
    User getUser(Long id);
    int insertUser(User user);
    List<User> queryByUserName(String userName);
    int insertUsers(List<User> userList);
}
