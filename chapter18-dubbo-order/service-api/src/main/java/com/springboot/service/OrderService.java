package com.springboot.service;

import com.springboot.infrastructure.pojo.Order;

public interface OrderService {
    Order getOrder(int orderId);

    boolean createOrder(int itemId);
}
