package com.springboot.chapter4.controller;

import com.springboot.chapter4.dao.JpaUserRepository;
import com.springboot.chapter4.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/jpa")
public class JpaController {
    @Autowired
    private JpaUserRepository jpaUserRepository;

    @RequestMapping("/getUser")
    @ResponseBody
    public User getUser(Long id) {
        // 使用JPA接口查询对象
        User user = jpaUserRepository.findById(id).get();
        return user;
    }

    @RequestMapping("/findByUserNameLike")
    @ResponseBody
    public List<User> findByUserNameLike(String userName) {
        List<User> users = jpaUserRepository.findByUserNameLike("%" + userName + "%");
        return users;
    }

    @RequestMapping("/findByUserNameLikeOrNoteLike")
    @ResponseBody
    public List<User> findByUserNameLikeOrNoteLike(String userName, String note) {
        String userNameLike = "%" + userName + "%";
        String noteLike = "%" + note + "%";
        List<User> users = jpaUserRepository.findByUserNameLikeOrNoteLike(userNameLike, noteLike);
        return users;
    }
}
