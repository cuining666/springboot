package com.springboot.chapter7.controller;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.springboot.chapter7.pojo.User;
import com.springboot.chapter7.repository.UserRepository;
import com.springboot.chapter7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    /**
     * 跳转到测试页面
     * @return
     */
    @RequestMapping("/page")
    public String page() {
        return "user";
    }

    /**
     * 保存（新增或更新）用户
     * @param user 用户
     * @return 用户信息
     */
    @RequestMapping("/save")
    @ResponseBody
    public User saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    /**
     * 获取用户
     * @param id
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public User getUser(Long id) {
        User user = userService.getUser(id);
        return user;
    }

    /**
     * 查询用户
     * @param userName 用户名
     * @param note 备注
     * @param skip 跳过用户个数
     * @param limit 限制返回用户个数
     * @return
     */
    @RequestMapping("/find")
    @ResponseBody
    public List<User> findUsers(String userName, String note, int skip, int limit) {
        List<User> userList = userService.findUsers(userName, note, skip, limit);
        return userList;
    }

    /**
     * 更新用户部分属性
     * @param id
     * @param userName
     * @param note
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public UpdateResult updateUser(Long id, String userName, String note) {
        return userService.updateUser(id, userName, note);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public DeleteResult deleteUser(Long id) {
        return userService.deleteUser(id);
    }

    @RequestMapping("/byName")
    @ResponseBody
    public List<User> findByUserName(String userName) {
        return userRepository.findByUserNameLike(userName);
    }

    @RequestMapping("/byIdAndName")
    @ResponseBody
    public User findByIdAndUserName(Long id, String userName) {
        return userRepository.find(id, userName);
    }

    @RequestMapping("/byIdOrName")
    @ResponseBody
    public User findByIdOrUserName(Long id, String userName) {
        return userRepository.findByIdOrUserName(id, userName);
    }
}
