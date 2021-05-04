package com.springboot.chapter9;

import com.springboot.chapter9.interceptor.Interceptor1;
import com.springboot.chapter9.interceptor.MultipartInterceptor1;
import com.springboot.chapter9.interceptor.MultipartInterceptor2;
import com.springboot.chapter9.interceptor.MultipartInterceptor3;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication(scanBasePackages = "com.springboot.chapter9")
@MapperScan(basePackages = "com.springboot.chapter9", annotationClass = Repository.class)
// 实现WebMvcConfigurer接口，将自定义拦截器类注册到spring mvc
public class Chapter9Application implements WebMvcConfigurer {

    // 国际化拦截器
    private LocaleChangeInterceptor lci;

    /**
     * 国际化解析器
     * 注意这个Bean name要是localeResolver，这是Spring MVC中的约定
     * @return
     */
    @Bean(name = "localeResolver")
    public LocaleResolver initLocaleResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // 默认国际化区域
        slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return slr;
    }

    /**
     * 创建国际化拦截器
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        if (lci != null) {
            return lci;
        }
        lci = new LocaleChangeInterceptor();
        // 设置拦截的参数名
        lci.setParamName("language");
        return lci;
    }

    /**
     * 注册多个拦截器
     * 对于处理器前方法采用先注册先执行，而处理器后方法和完成方法则是先注册后执行的规则
     * 处理器前（preHandle）方法会执行，但是一旦返回false ，则后续的拦截器、处理器和所有拦截器的处理器后（postHandle）方法都不会被执行
     * 完成方法（afterCompletion）则不一样，它只会执行返回true 的拦截器的完成方法，而且顺序是先注册后执行
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器到Spriing MVC机制，然后它会返回一个拦截器注册
//        InterceptorRegistration ir = registry.addInterceptor(new Interceptor1());
        InterceptorRegistration ir1 = registry.addInterceptor(new MultipartInterceptor1());
        InterceptorRegistration ir2 = registry.addInterceptor(new MultipartInterceptor2());
        InterceptorRegistration ir3 = registry.addInterceptor(new MultipartInterceptor3());
        // 指定拦截匹配模式，限制拦截器拦截请求
        ir1.addPathPatterns("/interceptor/*");
        ir2.addPathPatterns("/interceptor/*");
        ir3.addPathPatterns("/interceptor/*");
        // 给处理器增加国际化拦截器
        // 这里将通过国际化拦截器的preHandle方法对请求的国际化区域参数进行修改
        registry.addInterceptor(localeChangeInterceptor());
    }

    public static void main(String[] args) {
        SpringApplication.run(Chapter9Application.class, args);
    }

}
