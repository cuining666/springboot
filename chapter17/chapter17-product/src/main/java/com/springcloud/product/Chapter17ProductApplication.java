package com.springcloud.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

// 对于启动文件采用了很多注解，如@SpringBootApplication、@EnableDiscoveryClient和@EnableCircuitBreaker等。
// 这些注解有时候会让人觉得比较冗余，为了简化开发，Spring Cloud还提供了自己的注解＠SpringCloudApplication来简化使用Spring Cloud 的开发。
// 它目前还缺乏配置扫描包的配置项，所以往往需要配合注解@ComponentScan来定义扫描的包
@SpringCloudApplication
// 启动Feign，@SpringCloudApplication并不会启动Feign功能
@EnableFeignClients(basePackages = "com.springcloud.product")
@ComponentScan(basePackages = "com.springcloud.product")
public class Chapter17ProductApplication {

    /**
     * 初始化RestTemplate
     * @return
     */
    // 多节点负载均衡
    @LoadBalanced
    @Bean
    public RestTemplate initRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Chapter17ProductApplication.class, args);
    }

}
