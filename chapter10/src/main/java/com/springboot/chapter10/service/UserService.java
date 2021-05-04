package com.springboot.chapter10.service;

import com.springboot.chapter10.pojo.User;

import java.util.List;

public interface UserService {

    User getUser(Long id);

    User insertUser(User user);

    List<User> findUsers(String userName, String note, int start, int limit);

    void updateUser(User user);

    int updateUserName(Long id, String userName);

    int deleteUser(Long id);
}
