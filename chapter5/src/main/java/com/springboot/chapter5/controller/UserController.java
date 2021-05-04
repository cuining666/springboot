package com.springboot.chapter5.controller;

import com.springboot.chapter5.pojo.User;
import com.springboot.chapter5.service.UserBatchService;
import com.springboot.chapter5.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserBatchService userBatchService;

    @RequestMapping("/getUser")
    @ResponseBody
    public User getUser(Long id) {
        return userService.getUser(id);
    }

    @RequestMapping("/insertUser")
    @ResponseBody
    public Map<String, Object> insertUser(String userName, String note, int sex) {
        User user = new User();
        user.setUserName(userName);
        user.setNote(note);
        user.setSex(sex);
        int idx = userService.insertUser(user);
        List<User> result = userService.queryByUserName(userName);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", idx == 1);
        map.put("user", result.get(0));
        return map;
    }

    @RequestMapping("/insertUsers")
    @ResponseBody
    public Map<String, Object> insertUsers(String userName1, String note1, int sex1, String userName2, String note2, int sex2) {
        User user1 = new User();
        user1.setUserName(userName1);
        user1.setNote(note1);
        user1.setSex(sex1);
        User user2 = new User();
        user2.setUserName(userName2);
        user2.setNote(note2);
        user2.setSex(sex2);
        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        int idx = userService.insertUsers(users);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", idx > 0);
        map.put("user", users);
        return map;
    }
}
