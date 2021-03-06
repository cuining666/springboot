package com.springboot.chapter12.controller;

import com.springboot.chapter12.pojo.User;
import com.springboot.chapter12.service.ActiveMQService;
import com.springboot.chapter12.service.ActiveMQUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/activemq")
public class ActiveMQController {

    @Autowired
    private ActiveMQService activeMQService;
    @Autowired
    private ActiveMQUserService activeMQUserService;

    /**
     * 测试普通消息的发送
     * @param message
     * @return
     */
    @GetMapping("/msg")
    public Map<String, Object> msg(String message) {
        activeMQService.sendMsg(message);
        return result(true, message);
    }

    /**
     * 测试User对象的发送
     * @param id
     * @param userName
     * @param note
     * @return
     */
    @GetMapping("/user")
    public Map<String, Object> sendUser(Long id, String userName, String note) {
        User user = new User(id, userName, note);
        activeMQUserService.sendUser(user);
        return result(true, user);
    }

    private Map<String, Object> result(boolean success, Object message) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", message);
        return  result;
    }
}
