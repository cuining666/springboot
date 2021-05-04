package com.springboot.chapter10.controller;

import com.springboot.chapter10.enumeration.SexEnum;
import com.springboot.chapter10.exception.NotFoundException;
import com.springboot.chapter10.pojo.User;
import com.springboot.chapter10.service.UserService;
import com.springboot.chapter10.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

// 注解@RestController，通过它可以将控制器返回的对象转化为JSON数据集，不需要每一个方法都加入@ResponseBody
// 默认使用JSON视图
// 为了使得JSP视图也能够被渲染成功，原本通过直接返回字符串的方式就不能再用了，需采用ModelAndView的返回
@RestController
public class UserController2 {

    @Autowired
    private UserService userService;

    /**
     * 返回的是ModelAndView，则它会去处理这个ModelAndView，首先是解析这个View的类型，然后根据其返回，找到最好的视图解析器去处理。
     * BeanNameViewResolver ：根据请求U阳名称找到对应的视图。
     * ViewResolverComposite ：视图解析器组合。
     * Intema!Resourc巳ViewResolver ：逻辑视图解析器，也是最常用的解析器。
     * @return
     */
    @GetMapping("/restful2")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("restful2");
        return mv;
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

    private User changeToPo(UserVo userVo) {
        User user = new User();
        user.setId(userVo.getId());
        user.setUserName(userVo.getUserName());
        user.setSex(SexEnum.getEnumById(userVo.getSexCode()));
        user.setNote(userVo.getNote());
        return user;
    }

    /**
     * 控制器声明了@RestController，则默认会使用JSON数据集作为结果，那么它就会默认方法标注为applicatior咱son;charset=UTF-8。
     * 这样在控制器getUser方法结束后，Spring就会遍历注册好HttpMessageConverter接口的实现类，而其中已经注册好的MappingJackson2H忧pMessageConverter的
     * canWrit巳方法就会返回true，那么它就会启用MappingJackson2HttpMessageConverter将其转换为JSON
     * 数据集。
     * @param id
     * @return
     */
    @GetMapping("/user2/{id}")
    public UserVo getUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        return changeToVo(user);
    }

    // 产生JSON数据集
    @GetMapping(value = "/user2/exception/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    // 响应OK
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserVo getUserForException(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            // 如果找不到用户，则抛出异常，进入控制器通知
            throw new NotFoundException(1L, "用户【" + id + "】未找到");
        }
        return changeToVo(user);
    }

    /**
     * 因为@GetMapping的属性consumes声明为接收所有的请求体Body，所以它可以接收任何的请求体，而对于结果则声明为普通文本类，
     * 也就是修改了原有@RestController默认的JSON类型，同样结果也会被Spring MVC自身注册好的StringHttpMessageConverter拦截，
     * 这样就可以转变为一个简单的字符串
     * @param id
     * @return
     */
    @GetMapping(value = "/user2/name/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String getUserName(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        return user.getUserName();
    }

    @PostMapping("/user2/entity")
    public ResponseEntity<UserVo> insertUserEntity(@RequestBody UserVo userVo) {
        User user = changeToPo(userVo);
        userService.insertUser(user);
        UserVo result = changeToVo(user);
        HttpHeaders headers = new HttpHeaders();
        String success = (result == null || result.getId() == null) ? "false" : "true";
        // 设置响应头，比较常用的方式
        headers.add("success", success);
        // 下面是使用集合List方式，不是太常用
//        headers.put("success", Arrays.asList(success));
        // 返回创建成功的状态码
        return new ResponseEntity<UserVo>(result, headers, HttpStatus.CREATED);
    }

    @PostMapping("/user2/annotation")
    // 指定状态码为201(资源创建成功)
    @ResponseStatus(HttpStatus.CREATED)
    public UserVo insertUser(@RequestBody UserVo userVo) {
        User user = changeToPo(userVo);
        userService.insertUser(user);
        UserVo result = changeToVo(user);
        return result;
    }

}
