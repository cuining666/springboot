package com.springboot.chapter10.restTemplate;

import com.springboot.chapter10.pojo.User;
import com.springboot.chapter10.vo.UserVo;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestTemplateTest {

    /**
     * 获取用户
     * @param id
     * @return
     */
    public static UserVo getUser(Long id) {
        // 创建一个RestTemplate对象
        RestTemplate template = new RestTemplate();
        // 消费服务，第一个参数为url ，第二个是返回类型，第三个是URI路径参数
        UserVo userVo = template.getForObject("http://localhost:8080/user/{id}", UserVo.class, id);
        // 打印用户名
        System.out.println(userVo.getUserName());
        return userVo;
    }

    /**
     * 多参数http get请求
     * @param userName
     * @param note
     * @param start
     * @param limit
     * @return
     */
    public static List<UserVo> findUser(String userName, String note, int start, int limit) {
        RestTemplate template = new RestTemplate();
        // 使用Map封装多个参数，以提高可读性
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        params.put("note", note);
        params.put("start", start);
        params.put("limit", limit);
        // Map中的key和url中的参数以一一对应
        String url = "http://localhost:8080/user/{userName}/{note}/{start}/{limit}";
        // 请求后端
        ResponseEntity<List> responseEntity = template.getForEntity(url, List.class, params);
        List<UserVo> userVoList = responseEntity.getBody();
        return userVoList;
    }

    /**
     * 新增用户
     * @param userVo
     * @return
     */
    public static User insertUser(UserVo userVo) {
        RestTemplate template = new RestTemplate();
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        // 设置请求内容为JSON类型
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 创建请求实体对象
        HttpEntity<UserVo> request = new HttpEntity<>(userVo, headers);
        // 请求时传递请求实体对象，并返回回填id的用户
        User user = template.postForObject("http://localhost:8080/user", request, User.class);
        return user;
    }

    /**
     * 删除用户
     * @param id
     */
    public static void deleteUser(Long id) {
        RestTemplate template = new RestTemplate();
        template.delete("http://localhost:8080/user/{id}", id);
    }

    /**
     * 获取服务器晌应头属性和HTTP状态码
     * @param userVo
     * @return
     */
    public static User insertUserEntity(UserVo userVo) {
        RestTemplate template = new RestTemplate();
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        // 请求类型
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 绑定请求体和头
        HttpEntity<UserVo> request = new HttpEntity<>(userVo, headers);
        // 请求服务器
        ResponseEntity<User> responseEntity = template.postForEntity("http://localhost:8080/user2/entity", request, User.class);
        // 获取响应体
        User user = responseEntity.getBody();
        // 获取响应头
        HttpHeaders respHeaders = responseEntity.getHeaders();
        // 获取响应属性
        List<String> success = respHeaders.get("success");
        // 响应的Http响应码
        int status = responseEntity.getStatusCodeValue();
        System.out.println(user.getId());
        return user;
    }

    /**
     * 使用RestTemplate的exchange方法
     * @param userVo
     * @param id
     * @return
     */
    public static User useExchange(UserVo userVo, Long id) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserVo> request = new HttpEntity<>(userVo, headers);
        String url = "http://localhost:8080/user2/entity";
        ResponseEntity<User> userEntity = template.exchange(url, HttpMethod.POST, request, User.class);
        User user = userEntity.getBody();
        HttpHeaders respHeaders = userEntity.getHeaders();
        List<String> success = respHeaders.get("success");
        int status = userEntity.getStatusCodeValue();
        System.out.println(user.getId());
        url = "http://localhost:8080/user/{id}";
        ResponseEntity<UserVo> userVoEntity = template.exchange(url, HttpMethod.GET, null, UserVo.class, id);
        UserVo userVo1 = userVoEntity.getBody();
        System.out.println(userVo.getUserName());
        return user;
    }
}
