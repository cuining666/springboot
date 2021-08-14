package com.springboot.chapter12.controller;

import com.springboot.chapter12.service.RockeMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rocketmq")
public class RocketMQController {

    @Autowired
    private RockeMQService rockeMQService;

    @GetMapping("/send")
    public String sendMessage() {
        rockeMQService.sendMsg("my-rocketmq-topic", "rocketmq 消息发送测试");
        return "消息发送成功";
    }
}
