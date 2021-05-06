package com.springboot.chapter12.service;

/**
 * ActiveMQ服务接口
 */
public interface ActiveMQService {
    // 发送消息
    void sendMsg(String message);

    // 接受消息
    void receiveMsg(String message);
}
