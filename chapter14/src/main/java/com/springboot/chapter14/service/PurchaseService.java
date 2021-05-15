package com.springboot.chapter14.service;

import com.springboot.chapter14.pojo.Product;
import com.springboot.chapter14.pojo.PurchaseRecord;

import java.util.List;

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

    boolean purchaseRedis(Long userId, Long productId, int quantity);

    /**
     * 保存购买记录
     * @param purchaseRecordList
     * @return
     */
    boolean dealRedisPurchaser(List<PurchaseRecord> purchaseRecordList);

    void addProductRedis(String productName, String stock, String price);
}
