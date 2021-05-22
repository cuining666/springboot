package com.springboot.start.controller;

import com.springboot.service.OrderService;
import com.springboot.serviceapi.ItemService;
import com.springboot.start.service.PayService;
import com.springboot.start.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;

    @PostMapping("/buy")
    public JSONResult buyItem(int itemId, int quantity) {
        payService.buyItem(itemId, quantity);
        return JSONResult.ok();
    }
}
