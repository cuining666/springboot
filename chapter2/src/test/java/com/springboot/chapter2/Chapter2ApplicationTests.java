package com.springboot.chapter2;

import com.springboot.chapter2.config.AppConfig;
import com.springboot.chapter2.pojo.*;
import com.springboot.chapter2.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@SpringBootTest
@PropertySource(value = {"classpath:jdbc-test.properties"})
class Chapter2ApplicationTests {

	/**
	 * spring ioc测试
	 */
	@Test
	void iocPojoTest() {
		// 如果没有AppConfig类，则new AnnotationConfigApplicationContext(Chapter2Application.class)
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		User user = ctx.getBean(User.class);
		System.out.println(user.getId());
	}

	@Test
	void iocServiceTest() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		User user = ctx.getBean(User.class);
		UserService userService = ctx.getBean(UserService.class);
		userService.printUser(user);
	}

	@Test
	void iocPersonTest() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		Person person = ctx.getBean(BussinessPerson.class);
		person.service();
	}

	@Test
	void iocBeanScopeTest() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		ScopeBean scopeBean1 = ctx.getBean(ScopeBean.class);
		ScopeBean scopeBean2 = ctx.getBean(ScopeBean.class);
		System.out.println(scopeBean1 == scopeBean2);
	}
}
