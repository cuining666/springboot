package com.springcloud.product.service;

import com.springcloud.product.pojo.UserPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// 指定服务ID(Service ID)
@FeignClient("user")
public interface UserService {

    // 指定通过HTTP的GET方法请求路径
    @GetMapping("/user/{id}")
    // 这里会采用Spring MVC的注解配置
    public UserPo getUser(@PathVariable("id") Long id);

    @PostMapping("/insert")
    public Map<String, Object> addUser(@RequestBody UserPo userPo);

    @PostMapping("/update/{userName}")
    public Map<String, Object> updateUsername(@PathVariable("userName") String userName, @RequestHeader("id") Long id);

    @GetMapping("/timeout")
    public String timeout();
}
