package com.springboot.chapter6.controller;

import com.springboot.chapter6.pojo.User;
import com.springboot.chapter6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUser")
    @ResponseBody
    public User getUser(Long id) {
        return userService.getUser(id);
    }

    @RequestMapping("/insertUser")
    @ResponseBody
    public User insertUser(String userName, String note) {
        User user = new User();
        user.setUserName(userName);
        user.setNote(note);
        User result =userService.insertUser(user);
        return result;
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public Map<String, Object> updateUser(Long id, String userName) {
        User user = userService.updateByUserName(id, userName);
        boolean flag = user != null;
        String message = flag ? "更新成功" : "更新失败";
        return resultMap(flag, message);
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public Map<String, Object> deleteUser(Long id) {
        int result = userService.deleteUser(id);
        boolean flag = result == 1;
        String message = flag ? "删除成功" : "删除失败";
        return resultMap(flag, message);
    }

    private Map<String, Object> resultMap(boolean flag, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("message", message);
        return map;
    }
}
