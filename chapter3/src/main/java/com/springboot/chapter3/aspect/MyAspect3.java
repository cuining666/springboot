package com.springboot.chapter3.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

@Aspect
@Order(3)
public class MyAspect3 {
    @Pointcut("execution(* com.springboot.chapter3.service.impl.UserServiceImpl.manyAspects(..))")
    public void manyAspects(){

    }

    @Before("manyAspects()")
    public void before() {
        System.out.println("MyAspect3 before......");
    }

    @After("manyAspects()")
    public void after() {
        System.out.println("MyAspect3 after......");
    }

    @AfterReturning("manyAspects()")
    public void afterReturning() {
        System.out.println("MyAspect3 afterReturning......");
    }
}
