package com.springboot.chapter14.service;

import com.springboot.chapter14.pojo.Product;

public interface PurchaseService {

    /**
     * 处理购买业务
     * @param userId
     * @param productId
     * @param quantity
     * @return
     */
    boolean purchase(Long userId, Long productId, int quantity);

    Product addProduct(String productName, String stock, String price);
}
