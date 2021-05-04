package com.springboot.chapter2.pojo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
// ＠Primary的含义告诉Spring IoC容器，当发现有多个同样类型的Bean时,请优先使用我进行注入
@Primary
public class Cat implements Animal {
    @Override
    public void use() {
        System.out.println("猫【" + Cat.class.getSimpleName() + "】是抓老鼠的。");
    }
}
