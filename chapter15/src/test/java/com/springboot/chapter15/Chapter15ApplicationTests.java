package com.springboot.chapter15;

import com.springboot.chapter15.pojo.Product;
import com.springboot.chapter15.pojo.User;
import com.springboot.chapter15.service.ProductService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.math.BigDecimal;

// 使用随机端口启动测试服务
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Chapter15ApplicationTests {

    @MockBean
    private ProductService productService;

    // REST测试模板，Spring Boot自动提供
    @Autowired
    private TestRestTemplate template;

    @Test
    public void testGetUser() {
        // 请求当前启动的服务，请注意URI的缩写
        User user = template.getForObject("/user/{id}", User.class, 1L);
        Assert.assertNotNull(user);
    }

    @Test
    public void testGetProduct() {
        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setProductName("黄桃");
        mockProduct.setStock(50);
        mockProduct.setPrice(new BigDecimal("2.00"));
        mockProduct.setVersion(0);
        // 指定Mock Bean方法和参数
        BDDMockito.given(productService.getProduct(1L)).willReturn(mockProduct);
        // 进行mock测试
        Product product = productService.getProduct(1L);
        Assert.assertTrue(product.getProductName().equals("黄桃"));
    }

}
