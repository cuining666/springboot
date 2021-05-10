package com.springboot.chapter13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

// 定义扫描包
@SpringBootApplication(scanBasePackages = "com.springboot.chapter13")
// 因为引入了JPA ，所以在默认的情况下Spring Boot会尝试装配关系数据库数据源（DataSource)，而这里使用的MongoDB并没有关系数据库，所以使用＠EnableAutoConfiguration去排除数据源的初始化，否则将会得到错误的启动日志
// 通过＠EnableAutoConfiguration排除原有自动配置的数据源
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
// 在WebFlux中使用响应式的MongoDB的JPA接口，需要使用注解＠EnableReactiveMongoRepositories进行驱动
// 定义扫描包
@EnableReactiveMongoRepositories(basePackages = "com.springboot.chapter13.repository")
public class Chapter13Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter13Application.class, args);
    }

}
