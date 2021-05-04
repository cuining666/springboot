package com.springboot.chapter2.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@Component
@Data
// 有时候我们也可以使用注解＠ConfigurationProperties，通过它使得配置上有所减少
// ＠ConfigurationProperties中配置的字符串database，将与POJO 的属性名称组成属性的全限定名去配置文件里查找，这样就能将对应的属性读入到POJO当中
//@ConfigurationProperties("database")
public class DataBaseProperties {
//    @Value("${database.driverName}")
    private String driverName;
//    @Value("${database.url}")
    private String url;
//    @Value("${database.username}")
    private String username;
//    @Value("${database.password}")
    private String password;
    // ＠Value注解，既可以加载属性，也可以加在set方法上
}
