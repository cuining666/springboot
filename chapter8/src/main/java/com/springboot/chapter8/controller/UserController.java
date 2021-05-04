package com.springboot.chapter8.controller;

import com.springboot.chapter8.pojo.User;
import com.springboot.chapter8.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/details")
    public ModelAndView details(Long id) {
        // 访问模型层得到数据
        User user = userService.getUser(id);
        // 模型和视图
        ModelAndView mv = new ModelAndView();
        // 定义模型视图
        mv.setViewName("user/details");
        // 加入数据模型
        mv.addObject("user", user);
        // 返回模型视图
        return mv;
    }

    @RequestMapping("/detailsForJson")
    public ModelAndView detailsForJson(Long id) {
        // 访问模型层得到数据
        User user = userService.getUser(id);
        // 模型和视图
        ModelAndView mv = new ModelAndView();
        // 生成Json视图
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        mv.setView(jsonView);
        // 加入模型
        mv.addObject("user", user);
        return mv;
    }

    @RequestMapping("/table")
    public ModelAndView table() {
        List<User> userList = userService.findUsers(null, null);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/table");
        mv.addObject("userList", userList);
        return mv;
    }

    /**
     * 注解＠RequestParam，通过指定参数名称使得HTTP请求的参数和方法的参数进行绑定，只是这个注解的默认规则是参数不能为空。
     * 为了克服这个问题，代码将其属性required设置为false即可，其意义就是允许参数为空。
     * @param userName
     * @param note
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<User> list(@RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "note", required = false) String note) {
        List<User> userList = userService.findUsers(userName, note);
        return userList;
    }
}
