package com.springboot.chapter9.service.impl;

import com.springboot.chapter9.dao.UserDao;
import com.springboot.chapter9.pojo.User;
import com.springboot.chapter9.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void insertUser(User user) {
        userDao.insert(user);
    }

    @Override
    public User getUser(Long id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public List<User> findUsers(String userName, String note) {
        return userDao.selectByUserNameAndNote(userName, note);
    }
}
