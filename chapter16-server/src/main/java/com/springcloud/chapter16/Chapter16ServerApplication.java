package com.springcloud.chapter16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
// 启用Eureka
@EnableEurekaServer
public class Chapter16ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter16ServerApplication.class, args);
    }

}
