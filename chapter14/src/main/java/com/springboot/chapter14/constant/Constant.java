package com.springboot.chapter14.constant;

public interface Constant {
    // Redis购买记录集合前缀
    String PURCHASE_PRODUCT_LIST = "purchase_list_";
    String PRODUCT_PREFIX = "product_";
    // 抢购商品集合
    String PRODUCT_SCHEDULE_SET = "product_schedule_set";
    // 每次取出1000条，避免一次取出消耗太多内存
    int ONE_TIME_SIZE = 1000;
}
