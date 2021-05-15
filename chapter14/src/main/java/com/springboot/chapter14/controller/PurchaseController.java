package com.springboot.chapter14.controller;

import com.springboot.chapter14.pojo.Product;
import com.springboot.chapter14.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/add")
    public ModelAndView addProductPage() {
        ModelAndView mv = new ModelAndView("add");
        return mv;
    }

    @PostMapping("/add")
    public Map<String, Object> addProductRedis(String productName, String stock, String price) {
        Product product = purchaseService.addProduct(productName, stock, price);
        Map<String, Object> map = new HashMap<>();
        if (product == null || product.getId() == null) {
            map = result(false, "新增产品失败");
        } else {
            map = result(true, "新增产品成功");
        }
        return map;
    }

    @PostMapping("/redis/add")
    public Map<String, Object> addProduct(String productName, String stock, String price) {
        try {
            purchaseService.addProductRedis(productName, stock, price);
        } catch (Exception e) {
            e.printStackTrace();
            return result(false, "新增产品失败");
        }
        return result(true, "新增产品成功");
    }

    @GetMapping("/test")
    public ModelAndView testPage() {
        ModelAndView mv = new ModelAndView("test");
        return mv;
    }

    @PostMapping("/purchase")
    public Map<String, Object> purchase(Long userId, Long productId, Integer quantity) {
        boolean success = purchaseService.purchase(userId, productId, quantity);
        String message = success == true ? "购买成功" : "购买失败";
        return result(success, message);
    }

    @GetMapping("/redis/test")
    public ModelAndView testRedisPage() {
        ModelAndView mv = new ModelAndView("testRedis");
        return mv;
    }

    @PostMapping("/redis/purchase")
    public Map<String, Object> purchaseRedis(Long userId, Long productId, Integer quantity) {
        boolean success = purchaseService.purchaseRedis(userId, productId, quantity);
        String message = success == true ? "购买成功" : "购买失败";
        return result(success, message);
    }

    private Map<String, Object> result(boolean success, String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", message);
        return result;
    }
}
