package com.springcloud.chapter16dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
// 启用Hystrix仪表盘
@EnableHystrixDashboard
public class Chapter16DashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter16DashboardApplication.class, args);
    }

}
