package com.springboot.chapter9.service;

import com.springboot.chapter9.pojo.User;

import java.util.List;

public interface UserService {

    void insertUser(User user);

    User getUser(Long id);

    List<User> findUsers(String userName, String note);
}
