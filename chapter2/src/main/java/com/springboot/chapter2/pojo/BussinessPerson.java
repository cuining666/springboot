package com.springboot.chapter2.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BussinessPerson implements Person {

    @Autowired
    // 有时候＠Primary也可以使用在多个类上，也许无论是猫还是狗都可能带上＠Primary注解，其结果是IoC容器还是无法区分采用哪个Bean的实例进行注入
    // 那么＠Quelifier可以满足你的这个愿望。它的配置项value需要一个字符串去定义，它将与＠Autowired组合在一起，通过类型和名称一起找到Bean
    // Bean名称在Spring IoC容器中是唯一的标识，通过这个就可以消除歧义性了
    @Qualifier("squirrel")
    private Animal animal = null;

    @Override
    public void service() {
        animal.use();
    }

    @Override
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
