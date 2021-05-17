package com.springcloud.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.springcloud.user")
@EnableEurekaClient
public class Chapter17UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter17UserApplication.class, args);
    }

}
