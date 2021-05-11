package com.springboot.chapter13.controller;

import com.springboot.chapter13.pojo.User;
import com.springboot.chapter13.service.UserService;
import com.springboot.chapter13.validator.UserValidator;
import com.springboot.chapter13.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

// ＠RestController代表采用REST风格的控制器，这样Spring就知道将返回的内容转换为JSON数据序列
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // 添加局部验证器

    /**
     * 删除WebFluxConfig里的全局验证器，可以添加如下局部验证器
     * @param binder
     */
//    @InitBinder
//    public void initBinder(DataBinder binder) {
//        binder.setValidator(new UserValidator());
//    }

    @GetMapping("/user/{id}")
    public Mono<UserVo> getUser(@PathVariable Long id) {
        return userService.getUser(id).map(u -> translate(u));
    }

    @PostMapping("/user")
    public Mono<UserVo> insertUser(@RequestBody User user) {
        return userService.insertUser(user).map(u -> translate(u));
    }

    @PutMapping("/user")
    public Mono<UserVo> updateUser(@RequestBody User user) {
        return userService.updateUser(user).map(u -> translate(u));
    }

    @DeleteMapping("/user/{id}")
    public Mono<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/user/{userName}/{note}")
    public Flux<UserVo> findUsers(@PathVariable String userName, @PathVariable String note) {
        return userService.findUsers(userName, note).map(u -> translate(u));
    }

    @PostMapping("/user2/{user}")
    public Mono<UserVo> insertUser2(@PathVariable("user") User user) {
        return userService.insertUser(user).map(u -> translate(u));
    }

    @PostMapping("/user3")
    public Mono<UserVo> insertUser3(@Valid @RequestBody User user) {
        return userService.insertUser(user).map(u -> translate(u));
    }

    private UserVo translate(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUserName(user.getUserName());
        userVo.setSexCode(user.getSex().getCode());
        userVo.setSexName(user.getSex().getName());
        userVo.setNote(user.getNote());
        return userVo;
    }
}
