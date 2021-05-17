package com.springcloud.chapter16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.springcloud.chapter16")
@EnableEurekaClient
public class Chapter16UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter16UserApplication.class, args);
    }

}
