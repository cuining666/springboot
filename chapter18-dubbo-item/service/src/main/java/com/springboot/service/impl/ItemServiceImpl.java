package com.springboot.service.impl;

import com.springboot.infrastructure.dao.ItemMapper;
import com.springboot.infrastructure.pojo.Item;
import com.springboot.serviceapi.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

// spring的注解，不是dubbo的，使用dubbo的会注册到zookeeper，无需provider xml
@Service("itemService")
public class ItemServiceImpl implements ItemService {

    final static Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

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
    public boolean reduceStock(int itemId, int quantity) {
        if (getItemStock(itemId) < quantity) {
            log.info("库存剩余{}件，用户购买{}件，库存不足", getItemStock(itemId), quantity);
            return false;
        }
        itemMapper.reduceStock(itemId, quantity);
        return true;
    }
}
