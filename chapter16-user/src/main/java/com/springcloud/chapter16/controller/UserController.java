package com.springcloud.chapter16.controller;

import com.springcloud.chapter16.pojo.UserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class UserController {

    private Logger log = Logger.getLogger(this.getClass().getName());
    // 服务发现客户端
    @Autowired
    private DiscoveryClient discoveryClient;

    // 获取用户
    @GetMapping("/user/{id}")
    public UserPo getUserPo(@PathVariable("id") Long id) {
        ServiceInstance service = discoveryClient.getInstances("USER").get(0);
        log.info("【" + service.getServiceId() + "】" + service.getHost() + ":" + service.getPort());
        UserPo userPo = new UserPo();
        userPo.setId(id);
        int level = (int) (id % 3 + 1);
        userPo.setLevel(level);
        userPo.setUserName("user_name_" + id);
        userPo.setNote("note_" + id);
        return userPo;
    }

    @PostMapping("/insert")
    public Map<String, Object> insertUser(@RequestBody UserPo userPo) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "插入用户信息【" + userPo.getUserName() + "】");
        return map;
    }

    @PostMapping("/update/{userName}")
    public Map<String, Object> updateUsername(@PathVariable("userName") String userName, @RequestHeader("id") Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "更新用户【" + id + "】名称【" + userName + "】成功");
        return map;
    }

    /**
     * 在Spring Cloud中断路器是由NetFlix的Hystrix实现的，它默认监控微服务之间的调用超时时间为2000ms(2s)，
     * 如果超过这个超时时间，它就会根据你的配置使用其他方法进行响应。
     * @return
     */
    @GetMapping("/timeout")
    public String timeout() {
        // 生成一个3000之内的随机数
        long time = (long) (3000 * Math.random());
        try {
            // 程序延迟，有一定的概率超过2000ms
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "熔断测试";
    }
}
