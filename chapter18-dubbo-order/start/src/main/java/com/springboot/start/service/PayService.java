package com.springboot.start.service;

import java.util.Map;

public interface PayService {
    Map<String, Object> buyItem(int itemId, int quantity);
}
