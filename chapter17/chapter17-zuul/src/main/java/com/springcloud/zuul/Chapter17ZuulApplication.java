package com.springcloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication(scanBasePackages = "com.springcloud.zuul")
// 启动Zuul代理功能
// 从注解中可以看到，Zuul己经引入了断路机制，之所以引入断路机制，是因为在请求不到的时候，会进行断路，以避免网关发生请求无法释放的场景，导致微服务瘫痪。
@EnableZuulProxy
public class Chapter17ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter17ZuulApplication.class, args);
    }

}
