package com.springboot.chapter12.service.impl;

import com.springboot.chapter12.service.ActiveMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ActiveMQServiceImpl implements ActiveMQService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMsg(String message) {
        System.out.println("发送消息【" + message + "】");
        jmsTemplate.convertAndSend(message);
        // 自定义发送地址
//        jmsTemplate.convertAndSend("my-destination", message);
    }

    @Override
    // 使用注解，监昕地址发送过来的消息
    @JmsListener(destination = "${spring.jms.template.default-destination}")
    public void receiveMsg(String message) {
        System.out.println("接收消息【" + message + "】");
    }
}
