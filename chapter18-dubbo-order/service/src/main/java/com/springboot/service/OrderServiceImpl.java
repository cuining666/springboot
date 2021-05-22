package com.springboot.service;

import com.springboot.infrastructure.dao.OrderMapper;
import com.springboot.infrastructure.pojo.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 5)
    public Order getOrder(int orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 5)
    public boolean createOrder(int itemId) {
        String orderNum = UUID.randomUUID().toString().replace("-", "");
        Order order = new Order();
        order.setItemId(itemId);
        order.setOrderNum(orderNum);
        orderMapper.insert(order);
        if (order.getId() == null) {
            log.info("创建订单失败");
            return false;
        }
        log.info("创建订单成功");
        return true;
    }
}
