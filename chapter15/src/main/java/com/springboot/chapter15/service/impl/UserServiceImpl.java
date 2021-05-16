package com.springboot.chapter15.service.impl;

import com.springboot.chapter15.dao.UserDao;
import com.springboot.chapter15.pojo.User;
import com.springboot.chapter15.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(Long id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public User insertUser(User user) {
        userDao.insert(user);
        return user;
    }

    @Override
    public List<User> findUsers(String userName, String note, int start, int limit) {
        List<User> userList = userDao.selectByUserNameAndNote(userName, note, start, limit);
        return userList;
    }

    @Override
    public void updateUser(User user) {
        userDao.updateByPrimaryKey(user);
    }

    @Override
    public int updateUserName(Long id, String userName) {
        int result = userDao.updateUserNameById(id, userName);
        return result;
    }

    @Override
    public int deleteUser(Long id) {
        int result = userDao.deleteByPrimaryKey(id);
        return result;
    }
}
