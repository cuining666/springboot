package com.springboot.chapter3;

import com.springboot.chapter3.aspect.MyAspect;
import com.springboot.chapter3.aspect.MyAspect1;
import com.springboot.chapter3.aspect.MyAspect2;
import com.springboot.chapter3.aspect.MyAspect3;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.springboot.chapter3.*")
public class Chapter3Application {

	// 定义切面
	@Bean(name = "myAspect")
	public MyAspect initMyAspect() {
		return new MyAspect();
	}

	// 定义切面
	@Bean(name = "myAspect1")
	public MyAspect1 initMyAspect1() {
		return new MyAspect1();
	}

	// 定义切面
	@Bean(name = "myAspect2")
	public MyAspect2 initMyAspect2() {
		return new MyAspect2();
	}

	// 定义切面
	@Bean(name = "myAspect3")
	public MyAspect3 initMyAspect3() {
		return new MyAspect3();
	}

	// 启动切面
	public static void main(String[] args) {
		SpringApplication.run(Chapter3Application.class, args);
	}

}
