package com.springboot.chapter14.service.impl;

import com.springboot.chapter14.dao.ProductDao;
import com.springboot.chapter14.dao.PurchaseRecordDao;
import com.springboot.chapter14.pojo.Product;
import com.springboot.chapter14.pojo.PurchaseRecord;
import com.springboot.chapter14.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private PurchaseRecordDao purchaseRecordDao;

    @Override
    @Transactional
    public boolean purchase(Long userId, Long productId, int quantity) {
        // 获取产品
        Product product = productDao.selectByPrimaryKey(productId);
        // 比较库存和购买数量
        if (product.getStock() < quantity) {
            // 库存不足
            return false;
        }
        // 扣减库存
        productDao.decreaseProduct(productId, quantity);
        // 初始化购买记录
        PurchaseRecord purchaseRecord = initPurchaseRecord(userId, product, quantity);
        // 插入购买记录
        purchaseRecordDao.insert(purchaseRecord);
        return true;
    }

    @Override
    @Transactional
    public Product addProduct(String productName, String stock, String price) {
        Product product = new Product();
        product.setProductName(productName);
        product.setStock(Integer.parseInt(stock));
        product.setPrice(new BigDecimal(price));
        product.setVersion(0);
        productDao.insert(product);
        return product;
    }

    /**
     * 初始化购买信息
     * @param userId
     * @param product
     * @param quantity
     * @return
     */
    private PurchaseRecord initPurchaseRecord(Long userId, Product product, int quantity) {
        PurchaseRecord pr = new PurchaseRecord();
        pr.setPurchaseDate(new Date());
        pr.setPrice(product.getPrice());
        pr.setProductId(product.getId());
        pr.setQuantity(quantity);
        BigDecimal sum = product.getPrice().multiply(new BigDecimal(quantity));
        pr.setSum(sum);
        pr.setUserId(userId);
        return pr;
    }
}
