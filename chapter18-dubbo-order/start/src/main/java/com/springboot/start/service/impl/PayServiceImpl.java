package com.springboot.start.service.impl;

import com.springboot.service.OrderService;
import com.springboot.serviceapi.ItemService;
import com.springboot.start.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemService itemService;

    @Override
    public void buyItem(int itemId, int quantity) {
        boolean flag = itemService.reduceStock(itemId, quantity);
        if (flag) {
            orderService.createOrder(itemId);
        }
    }
}
