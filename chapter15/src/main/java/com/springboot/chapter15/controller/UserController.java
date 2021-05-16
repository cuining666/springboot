package com.springboot.chapter15.controller;

import com.springboot.chapter15.pojo.User;
import com.springboot.chapter15.service.UserService;
import com.springboot.chapter15.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// 注解@RestController，通过它可以将控制器返回的对象转化为JSON数据集，不需要每一个方法都加入@ResponseBody
// 默认使用JSON视图
// 为了使得JSP视图也能够被渲染成功，原本通过直接返回字符串的方式就不能再用了，需采用ModelAndView的返回
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 控制器声明了@RestController，则默认会使用JSON数据集作为结果，那么它就会默认方法标注为applicatior咱son;charset=UTF-8。
     * 这样在控制器getUser方法结束后，Spring就会遍历注册好HttpMessageConverter接口的实现类，而其中已经注册好的MappingJackson2H忧pMessageConverter的
     * canWrit巳方法就会返回true，那么它就会启用MappingJackson2HttpMessageConverter将其转换为JSON
     * 数据集。
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public UserVo getUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        return changeToVo(user);
    }

    private UserVo changeToVo(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUserName(user.getUserName());
        userVo.setSexCode(user.getSex().getId());
        userVo.setSexName(user.getSex().getName());
        userVo.setNote(user.getNote());
        return userVo;
    }

}
