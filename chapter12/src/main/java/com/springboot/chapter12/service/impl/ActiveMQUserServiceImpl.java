package com.springboot.chapter12.service.impl;

import com.springboot.chapter12.pojo.User;
import com.springboot.chapter12.service.ActiveMQUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ActiveMQUserServiceImpl implements ActiveMQUserService {

    private static final String MY_DESTINATION = "my-destination";
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendUser(User user) {
        System.out.println("发送消息【" + user + "】");
        // 使用自定义地址发送对象
        jmsTemplate.convertAndSend(MY_DESTINATION, user);
    }

    @Override
    // 监控自定义地址
    @JmsListener(destination = MY_DESTINATION)
    public void receiveUser(User user) {
        System.out.println("接收消息【" + user + "】");
    }
}
