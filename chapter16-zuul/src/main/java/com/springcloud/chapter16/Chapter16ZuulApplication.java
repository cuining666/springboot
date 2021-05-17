package com.springcloud.chapter16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
// 启动Zuul代理功能
@EnableZuulProxy
public class Chapter16ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter16ZuulApplication.class, args);
    }

}
