package com.springboot.chapter12.service;

import com.springboot.chapter12.pojo.User;

public interface ActiveMQUserService {

    void sendUser(User user);

    void receiveUser(User user);
}
