package com.springboot.chapter8.service;

import com.springboot.chapter8.pojo.User;

import java.util.List;

public interface UserService {
    List<User> findUsers(String userName, String note);

    User getUser(Long id);
}
