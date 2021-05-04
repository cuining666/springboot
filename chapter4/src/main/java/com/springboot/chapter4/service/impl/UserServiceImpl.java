package com.springboot.chapter4.service.impl;

import com.springboot.chapter4.dao.UserMapper;
import com.springboot.chapter4.pojo.User;
import com.springboot.chapter4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }
}
