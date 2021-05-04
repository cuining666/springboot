package com.springboot.chapter2.config;

import com.springboot.chapter2.condition.DatabaseConditional;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
// 意味着它会进行扫描，通过配置项basePackages定义扫描的包名，在没有定义的情况下，它只会扫描当前包和其子包下的路径
//@ComponentScan

// 加入了excludeFilters的配置，使标注了＠Service的类将不被IoC容器扫描注入，这样就可以把UserService类排除到Spring IoC容器中了
//@ComponentScan(basePackages = {"com.springboot.chapter2.*"}, excludeFilters = {@ComponentScan.Filter(classes = {Service.class})})

//@ComponentScan(basePackages = {"com.springboot.chapter2.pojo"})
@ComponentScan("com.springboot.chapter2.*")
// 引入xml配置bean
@ImportResource(value = {"classpath:spring-other.xml"})
public class AppConfig {
    // 自定义第三方Bean
    @Bean(name = "dataSource")
    // 条件装配Bean
    @Conditional(DatabaseConditional.class)
    public DataSource getDataSource(@Value("${database.driverName}") String driver,
                                    @Value("${database.url}") String url,
                                    @Value("${database.username}") String username,
                                    @Value("${database.password}") String password) {
        Properties properties = new Properties();
        properties.setProperty("driver",driver);
        properties.setProperty("url",url);
        properties.setProperty("username",username);
        properties.setProperty("password",password);
        DataSource dataSource = null;
        try {
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}
