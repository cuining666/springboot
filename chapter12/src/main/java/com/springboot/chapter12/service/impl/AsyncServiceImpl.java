package com.springboot.chapter12.service.impl;

import com.springboot.chapter12.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService {
    @Override
    // 声明使用异步调用
    @Async
    public void generateReport() {
        System.out.println("报表线程名称：" + "【" + Thread.currentThread().getName() + "】");
    }
}
