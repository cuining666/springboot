package com.springboot.chapter15;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@SpringBootApplication(scanBasePackages = "com.springboot.chapter15")
@MapperScan(basePackages = "com.springboot.chapter15", annotationClass = Repository.class)
@EnableWebSecurity
public class Chapter15Application extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 密码编码器
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 使用内存存储
        auth.inMemoryAuthentication()
                // 设置密码编码器
                .passwordEncoder(passwordEncoder)
                // 注册用户admin，密码为admin，并赋予USER和ADMIN的角色权限
                .withUser("admin")
                // 可通过passwordEncoder.encode("admin")得到加密后的密码
                .password("$2a$10$Q8ReR0DAIsm6FKc/VjPFp.19l3ORQMmnDEHNZNAthqhMZgMHXbW2C")
                // 赋予角色ROLE_USER和ROLE_ADMIN
                .roles("USER","ADMIN")
                .and()
                // 注册用户myuser，密码为123456，并赋予USER的角色权限
                .withUser("myuser")
                .password("$2a$10$BcwRsEXcHirmqQzVcj2UVOrqEQdlpohG5qV8qvHxB2rSpuU8lX6Oq")
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 需要Spring Security保护的端点
        String[] endPoints = {"auditevents", "beans", "conditions", "configprops", "env", "flyway", "httptrace", "loggers", "liquibase", "metrics", "mappings", "scheduledtasks", "sessions", "shutdown", "threaddump"};
        // 定义需要验证的端点
        http.csrf().disable().requestMatcher(EndpointRequest.to(endPoints))
                // 签名后登录
                .authorizeRequests().antMatchers("/**")
                // 要求登录用户拥有ADMIN角色
                .hasRole("ADMIN")
                .and()
                // 请求关闭页面需要ADMIN角色
                .antMatcher("/close").authorizeRequests().anyRequest().hasRole("ADMIN")
                .and()
                // 启用Http基础验证
                .httpBasic();
    }

    public static void main(String[] args) {
        SpringApplication.run(Chapter15Application.class, args);
    }

}
