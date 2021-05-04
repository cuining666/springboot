package com.springboot.chapter6;

import com.springboot.chapter6.config.RedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class Chapter6ApplicationTests {

    @Test
    void testRedis() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RedisConfig.class);
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        redisTemplate.opsForValue().set("key1","value1");
        redisTemplate.opsForHash().put("hash","field","hvalue");
    }

    @Test
    void testRedisSendMsg() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RedisConfig.class);
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        // 发送消息，等同于Redis客户端命令：publish topicl topic1's message
        redisTemplate.convertAndSend("topic1", "topic1's message");
    }

}
