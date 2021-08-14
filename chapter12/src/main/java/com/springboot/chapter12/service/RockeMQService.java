package com.springboot.chapter12.service;

public interface RockeMQService {

    // 发送普通消息
    void sendMsg(String topic, String message);

    // 发送异步消息
    void sendAsyncMsg(String topic, String message);

    // 发送延迟消息
    void sendDelayMsg(String topic, String message, int delayLevel);

    // 发送有Tag的消息
    void sengTagMsg(String topic, String message, String tag);
}
