package com.springboot.chapter2.service;

import com.springboot.chapter2.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public void printUser(User user) {
        System.out.println("编号: " + user.getId());
        System.out.println("用户名称: " + user.getUsername());
        System.out.println("备注: " + user.getNote());
    }
}
