package com.springboot.chapter13.client;

import com.springboot.chapter13.enumeration.SexEnum;
import com.springboot.chapter13.pojo.User;
import com.springboot.chapter13.vo.UserVo;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chapter13WebClient {
    public static void main(String[] args) {
        WebClient client = WebClient.create("http://localhost:8080");
        User newUser = new User();
        newUser.setId(11L);
        newUser.setUserName("user_name_new");
        newUser.setNote("note_new");
        newUser.setSex(SexEnum.MALE);
        insertUser(client, newUser);
        getUser(client, 12L);
        User upUser = new User();
        upUser.setId(11L);
        upUser.setUserName("user_name_update");
        upUser.setNote("note_update");
        upUser.setSex(SexEnum.FEMALE);
        updateUser(client, upUser);
        findUsers(client, "user", "note");
        deleteUsers(client, 12L);
    }

    private static void deleteUsers(WebClient client, long id) {
        Mono<Void> result =
                client.delete()
                        .uri("/user/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(Void.class);
        Void voidResult = result.block();
        System.out.println(voidResult);
    }

    private static void findUsers(WebClient client, String userName, String note) {
        // 定义参数map
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userName", userName);
        paramMap.put("note", note);
        Flux<UserVo> userFlux =
                // 定义GET请求，使用Map传递多个参数
                client.get().uri("/user/{userName}/{note}", paramMap)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToFlux(UserVo.class);
        // 通过Iterator遍历结果数据流，执行后服务器才会响应
        Iterator<UserVo> iterator = userFlux.toIterable().iterator();
        while (iterator.hasNext()) {
            UserVo item = iterator.next();
            System.out.println("【用户名称】" + item.getUserName());
        }
    }

    private static void updateUser(WebClient client, User upUser) {
        Mono<UserVo> userMono =
                // 定义PUT请求
                client.put()
                        .uri("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(upUser), User.class)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(UserVo.class);
        UserVo user = userMono.block();
        System.out.println("【用户名称】" + user.getUserName());
    }

    private static void getUser(WebClient client, long id) {
        Mono<UserVo> userMono =
                // 定义GET请求
                client.get()
                        // 定义请求URI和参数
                        .uri("/user/{id}", id)
                        // 接收请求结果类型
                        .accept(MediaType.APPLICATION_JSON)
                        // 设置请求结果检索规则
                        .retrieve()
                        // 将结果体转换为一个Mono封装的数据流
                        .bodyToMono(UserVo.class);
        UserVo user = userMono.block();
        System.out.println("【用户名称】" + user.getUserName());
    }

    private static void insertUser(WebClient client, User newUser) {
        // 注意，这只是定义一个时间，并不会发送请求
        Mono<UserVo> userMono =
                // 定义POST请求
                client.post()
                        // 设置请求URI
                        .uri("/user")
                        // 请求体为JSON数据流
                        .contentType(MediaType.APPLICATION_JSON)
                        // 请求体内容
                        .body(Mono.just(newUser), User.class)
                        // 接收请求结果类型
                        .accept(MediaType.APPLICATION_JSON)
                        // 设置请求结果检索规则
                        .retrieve()
                        // 将结果体转换为一个Mono封装的数据流
                        .bodyToMono(UserVo.class);
        // 获取服务器发布的数据流，此时才会发送请求
        UserVo user = userMono.block();
        System.out.println("【用户名称】" + user.getUserName());
    }
}
