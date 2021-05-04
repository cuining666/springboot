package com.springboot.chapter8.service.impl;

import com.springboot.chapter8.dao.UserDao;
import com.springboot.chapter8.pojo.User;
import com.springboot.chapter8.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findUsers(String userName, String note) {
        return userDao.selectByUserNameAndNote(userName, note);
    }

    @Override
    public User getUser(Long id) {
        return userDao.selectByPrimaryKey(id);
    }

}
