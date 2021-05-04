package com.springboot.chapter2.pojo;

public class Squirrel implements Animal {
    @Override
    public void use() {
        System.out.println("松鼠【" + Squirrel.class.getSimpleName() + "】可以采集松果。");
    }
}
