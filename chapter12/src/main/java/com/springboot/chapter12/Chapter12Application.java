package com.springboot.chapter12;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// 注解驱动定时任务
//@EnableScheduling
public class Chapter12Application {

    // 消息队列名称
    @Value("${rabbitmq.queue.msg}")
    private String msgQueueName;
    // 用户队列名称
    @Value("${rabbitmq.queue.user}")
    private String userQueueName;

    @Bean
    public Queue createQueueMsg() {
        // 创建字符串消息队列，boolean值代表是否持久化消息
        return new Queue(msgQueueName, true);
    }

    @Bean
    public Queue createQueueUser() {
        // 创建用户消息队列，boolean值代表是否持久化消息
        return new Queue(userQueueName, true);
    }

    public static void main(String[] args) {
        SpringApplication.run(Chapter12Application.class, args);
    }

}
