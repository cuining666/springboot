package com.springboot.chapter2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@SpringBootApplication
// 如果没有AppConfig类，定义扫描放在*Application类里
//@ComponentScan(basePackages = {"com.springboot.chapter2.*"}, excludeFilters = {@ComponentScan.Filter(classes = {Service.class})})
// ignoreResourceNotFound的默认值为false，也就是没有找到属性文件，就会报错，这里配置为true，也就是找不到就忽略掉，不会报错
@PropertySource(value = {"classpath:jdbc.properties"}, ignoreResourceNotFound = true)
public class Chapter2Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter2Application.class, args);
	}

}
