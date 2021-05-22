package com.springboot.serviceapi;

import com.springboot.infrastructure.pojo.Item;

public interface ItemService {
    Item getItem(int itemId);

    int getItemStock(int itemId);

    void reduceStock(int itemId, int quantity);
}
