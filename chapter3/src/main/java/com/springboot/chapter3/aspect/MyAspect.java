package com.springboot.chapter3.aspect;

import com.springboot.chapter3.pojo.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 定义切点
 */
@Aspect
public class MyAspect {

    @Pointcut("execution(* com.springboot.chapter3.service.impl.UserServiceImpl.printUser(..))")
    public void pointCut(){

    }

    @Before("pointCut() && args(user)")
    public void before(JoinPoint joinPoint, User user) {
        // 在前置通知中获取参数
        Object[] args = joinPoint.getArgs();
        System.out.println("before......");
    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("around before......");
        proceedingJoinPoint.proceed();
        System.out.println("around after......");
    }

    @After("pointCut()")
    public void after() {
        System.out.println("after......");
    }

    @AfterReturning("pointCut()")
    public void afterReturning() {
        System.out.println("afterReturning......");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing() {
        System.out.println("afterThrowing......");
    }

}
