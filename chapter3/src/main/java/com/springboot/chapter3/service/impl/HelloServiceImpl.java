package com.springboot.chapter3.service.impl;

import com.springboot.chapter3.service.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String name) {
        if(name == null || "".equals(name.trim())) {
            throw new RuntimeException("parameter is null!!!");
        }
        System.out.println("Hello " + name);
    }
}
