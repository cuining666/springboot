package com.springboot.chapter12.controller;

import com.springboot.chapter12.pojo.User;
import com.springboot.chapter12.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @GetMapping("/msg")
    public Map<String, Object> msg(String message) {
        rabbitMQService.sendMsg(message);
        return result(true, message);
    }

    @GetMapping("/user")
    public Map<String, Object> user(Long id, String userName, String note) {
        User user = new User(id, userName, note);
        rabbitMQService.sendUser(user);
        return result(true, user);
    }

    private Map<String, Object> result(boolean success, Object message) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", message);
        return result;
    }
}
