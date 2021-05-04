package com.springboot.chapter9.controller;

import com.springboot.chapter9.pojo.User;
import com.springboot.chapter9.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @SessionAttribute应用于参数
 * @SessionAttributes则只能用于类的注解
 */
// @SessionAttributes指定数据模型名称或者属性类型，保存到Session中
@SessionAttributes(names = {"user"}, types = Long.class)
@Controller
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private UserService userService;

    @GetMapping("/page")
    public String sessionPage() {
        return "/session/page";
    }

    @GetMapping("/test")
    // @SessionAttribute从HttpSession中取出数据，填充控制器方法参数
    public String test(@SessionAttribute("id") Long id, Model model) {
        // 根据类型保存到session中
        model.addAttribute("id_new", id);
        User user = userService.getUser(id);
        // 根据名称保存到session中
        model.addAttribute("user", user);
        return "session/test";
    }
}
