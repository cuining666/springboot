package com.springboot.chapter12.service.impl;

import com.springboot.chapter12.service.RockeMQService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RocketMQServiceImpl implements RockeMQService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Value("${rocketmq.producer.send-message-timeout}")
    private Integer messageTimeout;

    @Override
    public void sendMsg(String topic, String message) {
        rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(message).build());
    }

    @Override
    public void sendAsyncMsg(String topic, String message) {
        rocketMQTemplate.asyncSend(topic, MessageBuilder.withPayload(message).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 处理消息发送成功逻辑
            }

            @Override
            public void onException(Throwable throwable) {
                // 处理消息发送异常逻辑
            }
        });
    }

    @Override
    public void sendDelayMsg(String topic, String message, int delayLevel) {
        rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(message).build(), messageTimeout, delayLevel);
    }

    @Override
    public void sengTagMsg(String topic, String message, String tag) {
        rocketMQTemplate.syncSend(topic + ":" + tag, MessageBuilder.withPayload(message).build());
    }
}
