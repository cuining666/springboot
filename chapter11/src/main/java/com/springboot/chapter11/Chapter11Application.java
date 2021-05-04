package com.springboot.chapter11;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Repository;

@SpringBootApplication(scanBasePackages = "com.springboot.chapter11")
@MapperScan(basePackages = "com.springboot.chapter11", annotationClass = Repository.class)
@EnableCaching
public class Chapter11Application {
	public static void main(String[] args) {
		SpringApplication.run(Chapter11Application.class, args);
	}
}
