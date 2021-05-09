package com.springboot.chapter12.service;

import com.springboot.chapter12.pojo.User;

public interface RabbitMQService {
    // 发送消息
    void sendMsg(String message);
    // 发送用户
    void sendUser(User user);
}
