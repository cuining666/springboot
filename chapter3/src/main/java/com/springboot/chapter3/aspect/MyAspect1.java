package com.springboot.chapter3.aspect;

import com.springboot.chapter3.pojo.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

@Aspect
@Order(1)
public class MyAspect1 {
    @Pointcut("execution(* com.springboot.chapter3.service.impl.UserServiceImpl.manyAspects(..))")
    public void manyAspects(){

    }

    @Before("manyAspects()")
    public void before() {
        System.out.println("MyAspect1 before......");
    }

    @After("manyAspects()")
    public void after() {
        System.out.println("MyAspect1 after......");
    }

    @AfterReturning("manyAspects()")
    public void afterReturning() {
        System.out.println("MyAspect1 afterReturning......");
    }
}
