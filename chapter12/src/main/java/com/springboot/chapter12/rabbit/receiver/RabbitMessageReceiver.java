package com.springboot.chapter12.rabbit.receiver;

import com.springboot.chapter12.pojo.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMessageReceiver {

    // 定义监听字符串队列名称
    @RabbitListener(queues = "${rabbitmq.queue.msg}")
    public void receiveMessage(String msg) {
        System.out.println("收到消息：【" + msg + "】");
    }

    @RabbitListener(queues = "${rabbitmq.queue.user}")
    public void receiveUser(User user) {
        System.out.println("收到用户信息：【" + user + "】");
    }
}
