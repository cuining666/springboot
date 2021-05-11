package com.springboot.chapter13.config;

import com.springboot.chapter13.enumeration.SexEnum;
import com.springboot.chapter13.pojo.User;
import com.springboot.chapter13.validator.UserValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.concurrent.TimeUnit;

// 实现Java8的接口WebFluxConfigurer，该接口都是default方法
@Configuration
public class WebFluxConfig implements WebFluxConfigurer {
    // 注册Converter
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToUserConverter());
    }

    // 注册全局验证器
    @Override
    public Validator getValidator() {
        return new UserValidator();
    }

    // 设置静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                // 注册资源，可以通过URI访问
                .addResourceHandler("/resources/static/**")
                // 注册Spring资源，可以在Spring机制中访问
                .addResourceLocations("/public", "classpath:/static/")
                // 缓存一年（365 天）
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }

    /**
     * 实际上这样写还是有点冗余，这里想表达的是，可以通过继承实现WebFluxConfigurer接口，覆盖对应的方法，来自定义所需的一些组件。
     * 最为简单的方法是删除addFormatters方法，而将转换器定义为一个Spring Bean, Spring Boot就会自动识别这个Bean为转换器，而无须再自行进行注册。
     * @return
     */
    // 定义String -> User类型转换器
    // 如果定义为Spring Bean, Spring Boot会自动识别为类型转换器
    //@Bean
    public Converter<String, User> stringToUserConverter() {
        Converter<String, User> converter = new Converter<String, User>() {
            @Override
            public User convert(String src) {
                String[] strArr = src.split("-");
                User user = new User();
                Long id = Long.parseLong(strArr[0]);
                user.setId(id);
                user.setUserName(strArr[1]);
                Integer sexCode = Integer.parseInt(strArr[2]);
                SexEnum sexEnum = SexEnum.getSexEnum(sexCode);
                user.setSex(sexEnum);
                user.setNote(strArr[3]);
                return user;
            }
        };
        return converter;
    }
}
