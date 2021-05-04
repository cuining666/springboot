package com.springboot.chapter3;

import com.springboot.chapter3.intercept.MyInterceptor;
import com.springboot.chapter3.proxy.ProxyBean;
import com.springboot.chapter3.service.HelloService;
import com.springboot.chapter3.service.impl.HelloServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Chapter3ApplicationTests {

	@Test
	void testProxy() {
		HelloService helloService = new HelloServiceImpl();
		// 按约定获取proxy
		HelloService proxy = (HelloService) ProxyBean.getProxyBean(helloService, new MyInterceptor());
		proxy.sayHello("zhangsan");
		System.out.println("\n###################name is null!!!###################\n");
		proxy.sayHello(null);
	}

}
