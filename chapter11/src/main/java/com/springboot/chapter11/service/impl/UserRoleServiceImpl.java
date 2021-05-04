package com.springboot.chapter11.service.impl;

import com.springboot.chapter11.dao.RoleDao;
import com.springboot.chapter11.dao.UserDao;
import com.springboot.chapter11.pojo.Role;
import com.springboot.chapter11.pojo.User;
import com.springboot.chapter11.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public User getUserByUserName(String userName) {
        return userDao.selectByUserName(userName);
    }

    @Override
    public List<Role> findRolesByUserName(String userName) {
        return roleDao.selectRolesByUserName(userName);
    }

}
