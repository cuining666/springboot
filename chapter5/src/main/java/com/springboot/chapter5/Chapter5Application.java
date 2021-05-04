package com.springboot.chapter5;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {"com.springboot.chapter5"})
@MapperScan(basePackages = "com.springboot.chapter5", annotationClass = Repository.class)
public class Chapter5Application {
	// 注入事务管理器，它由spring boot自动生成
	@Autowired
	private PlatformTransactionManager platformTransactionManager;

	// 使用后初始化方法，观察自动生成的事务管理器
	@PostConstruct
	public void viewTransactionManager() {
		System.out.println(platformTransactionManager.getClass().getName());
	}

	public static void main(String[] args) {
		SpringApplication.run(Chapter5Application.class, args);
	}

}
