package com.springboot.chapter6;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = "com.springboot.chapter6")
// 指定扫描的Mybatis mapper
@MapperScan(basePackages = "com.springboot.chapter6", annotationClass = Repository.class)
// 驱动缓存的注解
@EnableCaching
public class Chapter6Application {

    @Autowired
    private RedisTemplate redisTemplate;
    // Redis连接工厂
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    // Redis消息监听器
    @Autowired
    private MessageListener redisMessageListener;
    // 任务池
    private ThreadPoolTaskScheduler taskScheduler;

    /**
     * 定义自定义后的初始方法
     */
    @PostConstruct
    public void init() {
        initRedisTemplate();
    }

    /**
     * 设置redisTemplate序列化器
     */
    private void initRedisTemplate() {
        RedisSerializer stringRedisSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
    }

    /**
     * 创建任务池，运行线程等待处理Redis消息
     * @return
     */
    @Bean
    public ThreadPoolTaskScheduler initTaskScheduler() {
        if(taskScheduler != null) {
            return taskScheduler;
        }
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        return taskScheduler;
    }

    /**
     * 定义Redis的监听容器
     * @return
     */
    @Bean
    public RedisMessageListenerContainer initRedisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // Redis连接工厂
        container.setConnectionFactory(redisConnectionFactory);
        // 设置运行任务池
        container.setTaskExecutor(initTaskScheduler());
        // 定义监听渠道，名称为topic1
        Topic topic = new ChannelTopic("topic1");
        // 使用监听器监听redis消息
        container.addMessageListener(redisMessageListener, topic);
        return container;
    }

    public static void main(String[] args) {
        SpringApplication.run(Chapter6Application.class, args);
    }

}
