package com.springboot.chapter7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

// 指定扫描的包
@SpringBootApplication(scanBasePackages = "com.springboot.chapter7")
// 指定扫描的包，用于扫描继承了MongoRepository的接口
// 使用自定义后缀，默认为Impl，在@EnableMongoRepositories中加repositoryImplementationPostfix = "Stuff"，并修改UserRepositoryImpl为UserRepositoryStuff
@EnableMongoRepositories(basePackages = "com.springboot.chapter7.repository")
public class Chapter7Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter7Application.class, args);
    }

}
