package com.springboot.chapter12.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 使用Java配置定义线程池和启用异步
 */
@Configuration
// 注解@EnableAsync代表开启Spring异步，这样就可以使用@Async驱动Spring使用异步
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    // 定义线程池
    @Override
    public Executor getAsyncExecutor() {
        // 定义线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(10);
        // 线程池最大线程数
        taskExecutor.setMaxPoolSize(30);
        // 线程队列最大线程数
        taskExecutor.setQueueCapacity(2000);
        // 初始化
        taskExecutor.initialize();
        return taskExecutor;
    }
}
