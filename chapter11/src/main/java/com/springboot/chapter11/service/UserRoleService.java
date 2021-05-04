package com.springboot.chapter11.service;

import com.springboot.chapter11.pojo.Role;
import com.springboot.chapter11.pojo.User;

import java.util.List;

public interface UserRoleService {

    User getUserByUserName(String userName);

    List<Role> findRolesByUserName(String userName);

}
