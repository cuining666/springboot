package com.springboot.chapter12.receiver.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "my-rocketmq-topic", selectorExpression = "*", consumerGroup = "rocketmq-test-group")
public class RocketMessageReveiver implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        byte[] bytes = message.getBody();
        String msg = new String(bytes);
        log.info("接收到消息：{}", msg);
    }
}
