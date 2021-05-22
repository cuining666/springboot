package com.springboot.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Repository;

@SpringBootApplication(scanBasePackages = "com.springboot")
@MapperScan(basePackages = "com.springboot.infastructure", annotationClass = Repository.class)
@ImportResource("classpath:dubbo-*.xml")
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

}
