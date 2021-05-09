package com.springboot.chapter12.service.impl;

import com.springboot.chapter12.pojo.User;
import com.springboot.chapter12.service.RabbitMQService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQServiceImpl implements RabbitTemplate.ConfirmCallback,RabbitMQService {

    @Value("${rabbitmq.queue.msg}")
    private String msgRouting;
    @Value("${rabbitmq.queue.user}")
    private String userRouting;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMsg(String message) {
        System.out.println("发送消息【" + message + "】");
        // 设置回调
        rabbitTemplate.setConfirmCallback(this);
        // 发送消息，通过msgRouting确定队列
        rabbitTemplate.convertAndSend(msgRouting, message);
    }

    @Override
    public void sendUser(User user) {
        System.out.println("发送用户" + user + "】");
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.convertAndSend(userRouting, user);
    }

    /**
     * 回调方法
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败：" + cause);
        }
    }
}
