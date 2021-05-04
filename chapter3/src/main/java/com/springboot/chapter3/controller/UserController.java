package com.springboot.chapter3.controller;

import com.springboot.chapter3.pojo.User;
import com.springboot.chapter3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 定义控制器
@Controller
// 定义类请求路径
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/print")
    // 转换为json
    @ResponseBody
    public User printUser(Long id, String username, String note) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setNote(note);
        userService.printUser(user);
        return user;
    }

    @RequestMapping("/manyAspects")
    public String manyAspects() {
        userService.manyAspects();
        return "manyAspects";
    }
}
