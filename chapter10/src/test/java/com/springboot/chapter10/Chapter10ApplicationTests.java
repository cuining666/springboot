package com.springboot.chapter10;

import com.springboot.chapter10.pojo.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

// 使用随机端口启动测试服务
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Chapter10ApplicationTests {

    // REST测试模板，Spring Boot自动提供
    @Autowired
    private TestRestTemplate template;

    @Test
    public void testGetUser() {
        // 请求当前启动的服务，请注意URI的缩写
        User user = template.getForObject("/user/{id}", User.class, 1L);
        Assert.assertNotNull(user);
    }

}
