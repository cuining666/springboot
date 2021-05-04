package com.springboot.chapter9.controller;

import com.springboot.chapter9.pojo.User;
import com.springboot.chapter9.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 数据模型
 */
@Controller
@RequestMapping("/data")
public class DataModelController {

    @Autowired
    private UserService userService;

    /**
     * 测试model接口
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/model")
    public String useModel(Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        // 这里返回字符串，在Spring MVC中，会自动创建ModelAndView且绑定名称
        return "data/user";
    }

    /**
     * 测试ModelMap类
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping("/modelMap")
    public ModelAndView useModelMap(Long id, ModelMap modelMap) {
        User user = userService.getUser(id);
        ModelAndView mv = new ModelAndView();
        // 设置视图名称
        mv.setViewName("/data/user");
        // 设置数据模型，此处model Map并没有与mv绑定，这步系统会自动处理
        modelMap.put("user", user);
        return mv;
    }

    /**
     * 测试ModelAndView类
     * @param id
     * @return
     */
    @GetMapping("/mav")
    public ModelAndView useModelAndView(Long id, ModelAndView mv) {
        User user = userService.getUser(id);
        // 设置视图名称
        mv.setViewName("/data/user");
        // 设置数据模型
        mv.addObject("user", user);
        return mv;
    }
}
