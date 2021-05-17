package com.springcloud.chapter16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.springcloud.chapter16")
// 启动Feign客户端
@EnableFeignClients(basePackages = "com.springcloud.chapter16")
// 启动断路机制
@EnableHystrix
public class Chapter16ProductApplication {

    /**
     * 初始化RestTemplate
     * @return
     */
    // 多节点负载均衡
    @LoadBalanced
    @Bean(name = "restTemplate")
    public RestTemplate initRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Chapter16ProductApplication.class, args);
    }

}
