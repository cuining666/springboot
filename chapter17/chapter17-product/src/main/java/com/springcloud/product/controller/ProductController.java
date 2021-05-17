package com.springcloud.product.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.springcloud.product.pojo.UserPo;
import com.springcloud.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    // 注入RestTemplate
    @Autowired
    private RestTemplate restTemplate;
    // 注入Feign接口
    @Autowired
    private UserService userService;

    @GetMapping("/ribbon")
    public UserPo testRibbon() {
        UserPo userPo = null;
        // 循环10次，然后可以看到各个用户微服务后台的日志打印
        for (int i = 0; i < 10; i++) {
            // 注意，这里直接使用了USER这个服务ID，代表用户微服务系统
            // USER这个字符串代替了服务器及其端口，这是一个服务ID(Service ID)，在Eureka 服务器中可以看到它的各个节点，
            // 它是用户微服务通过属性spring.application.name来指定的
            userPo = restTemplate.getForObject("http://USER/user/" + (i + 1), UserPo.class);
        }
        return userPo;
    }

    @GetMapping("/feign")
    public UserPo testFeign() {
        UserPo userPo = null;
        // 循环10次
        for (int i = 0; i < 10; i++) {
            Long id = (long) (i + 1);
            userPo = userService.getUser(id);
        }
        return userPo;
    }

    @GetMapping("/feign2")
    public Map<String, Object> testFeign2() {
        Map<String, Object> map = null;
        UserPo userPo = null;
        // 循环10次
        for (int i = 1; i <= 10; i++) {
            userPo = new UserPo();
            Long id = (long) i;
            userPo.setId(id);
            int level = i % 3 + 1;
            userPo.setUserName("user_name_" + id);
            userPo.setLevel(level);
            userPo.setNote("note_" + id);
            map = userService.addUser(userPo);
        }
        return map;
    }

    @GetMapping("/feign3")
    public Map<String, Object> testFeign3() {
        Map<String, Object> map = null;
        // 循环10次
        for (int i = 1; i <= 10; i++) {
            Long id = (long) i;
            String userName = "user_name_" + i;
            map = userService.updateUsername(userName, id);
        }
        return map;
    }

    /**
     * Ribbon断路
     * @return
     */
    @GetMapping("/hystrix1")
    // @HystrixCommand注解，它表示将在方法上启用断路机制，而其属性fallbackMethod则可以指定降级方法，指定为error，那么阵级方法就是error
    @HystrixCommand(fallbackMethod = "error",
            // 设置断路超时时间
            commandProperties = {
            @HystrixProperty(
                    name = "execution.isolation.thread.timeoutInMilliseconds",
                    value = "3000")})
    public String testHystrix1() {
        return restTemplate.getForObject("http://USER/timeout", String.class);
    }

    /**
     * Feign断路
     * @return
     */
    @GetMapping("/hystrix2")
    @HystrixCommand(fallbackMethod = "error")
    public String testHystrix2() {
        return userService.timeout();
    }

    /**
     * 降级服务方法
     * @return
     */
    public String error() {
        return "超时出错";
    }
}
