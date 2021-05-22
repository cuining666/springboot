package com.springboot.service.impl;

import com.springboot.infrastructure.dao.ItemMapper;
import com.springboot.infrastructure.pojo.Item;
import com.springboot.serviceapi.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

// spring的注解，不是dubbo的，使用dubbo的会注册到zookeeper，无需provider xml
@Service("itemService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 5)
    public Item getItem(int itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public int getItemStock(int itemId) {
        Item item = getItem(itemId);
        return item.getStock();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 5)
    public void reduceStock(int itemId, int quantity) {
        itemMapper.reduceStock(itemId, quantity);
    }
}
