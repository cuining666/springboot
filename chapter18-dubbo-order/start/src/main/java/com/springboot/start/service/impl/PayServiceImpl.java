package com.springboot.start.service.impl;

import com.springboot.service.OrderService;
import com.springboot.serviceapi.ItemService;
import com.springboot.start.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemService itemService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 5)
    public Map<String, Object> buyItem(int itemId, int quantity) {
        Map<String, Object> map = new HashMap<>();
        boolean flag = itemService.reduceStock(itemId, quantity);
        if (flag) {
            orderService.createOrder(itemId);
            map.put("success", false);
            map.put("message", "交易成功");
        } else {
            map.put("success", false);
            map.put("message", "扣减库存失败");
        }
        return map;
    }
}
