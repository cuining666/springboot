package com.springboot.chapter13.client;

import com.springboot.chapter13.enumeration.SexEnum;
import com.springboot.chapter13.pojo.User;
import com.springboot.chapter13.pojo.UserPojo;
import com.springboot.chapter13.vo.UserVo;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chapter13WebClient {
    public static void main(String[] args) {
        WebClient client = WebClient.create("http://localhost:8080");
//        User newUser = new User();
//        newUser.setId(11L);
//        newUser.setUserName("user_name_new");
//        newUser.setNote("note_new");
//        newUser.setSex(SexEnum.MALE);
//        insertUser(client, newUser);
//        insertUser2(client, "13-convert-1-note");
//        getUser(client, 12L);
        getUser2(client, 15L);
        getUserPojo(client, 13L);
//        User upUser = new User();
//        upUser.setId(11L);
//        upUser.setUserName("user_name_update");
//        upUser.setNote("note_update");
//        upUser.setSex(SexEnum.FEMALE);
//        updateUser(client, upUser);
//        findUsers(client, "user", "note");
//        deleteUsers(client, 12L);
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

    // 客户端处理服务端错误
    private static void getUser2(WebClient client, long id) {
        Mono<UserVo> userMono =
                client.get()
                        .uri("/user/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(
                                // 发生4开头或者5开头的状态码，4开头是客户端错误，5开头是服务器错误
                                // 第一个Lambda表达式，返回如果为true，则执行第二个Lambda表达式
                                status -> status.is4xxClientError() || status.is5xxServerError(),
                                // 如果发生异常，则用第二个表达式返回作为结果
                                // 第二个Lambda表达式
                                response -> Mono.empty())
                        .bodyToMono(UserVo.class);
        UserVo user = userMono.block();
        if (user != null) {
            System.out.println("【用户名称】" + user.getUserName());
        } else {
            System.out.println("服务器没有返回编号为：" + id + "的用户");
        }
    }

    /**
     * 使用自定义转换规则
     * @param client
     * @param id
     */
    private static void getUserPojo(WebClient client, long id) {
        Mono<UserPojo> userMono =
                client.get()
                        .uri("/user/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        // 启用交换，获取服务器发送过来的UserVo 对象
                        .exchangeToMono(
                                response -> {
                                    return response.bodyToMono(UserVo.class);
                                })
                        // 通过自定义方法转换为客户端的UserPojo
                        .map(userVo -> translate(userVo))
                        // 出现错误则返回空
                        .doOnError(ex -> Mono.empty());
        UserPojo user = userMono.block();
        if (user != null) {
            System.out.println("【用户名称】" + user.getUserName());
        } else {
            System.out.println("服务器没有返回编号为：" + id + "的用户");
        }
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

    private static void insertUser2(WebClient client, String userStr) {
        Mono<UserVo> userMono =
                client.post()
                        .uri("/user2/{user}", userStr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(UserVo.class);
        UserVo user = userMono.block();
        System.out.println("【用户名称】" + user.getUserName());
    }

    /**
     * 使用WebClient设置请求头
     * @param client
     * @param id
     * @param userName
     */
    private static void updateUserName(WebClient client, Long id, String userName) {
        Mono<UserVo> userMono =
                client.put()
                        .uri("/user/name", userName)
                        // 第一个请求头
                        .header("id", id + "")
                        // 第二个请求头
                        .header("userName", userName)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(
                                status -> status.is4xxClientError() || status.is5xxServerError(),
                                response -> Mono.empty())
                        .bodyToMono(UserVo.class);
        UserVo user = userMono.block();
        if (user != null) {
            System.out.println("【用户名称】" + user.getUserName());
        } else {
            System.out.println("服务器没有返回编号为：" + id + "的用户");
        }
    }

    // 转换方法
    private static UserPojo translate(UserVo vo) {
        if (vo == null) {
            return null;
        }
        UserPojo userPojo = new UserPojo();
        userPojo.setId(vo.getId());
        userPojo.setSex(vo.getSexCode() == 1 ? 1 : 2);
        userPojo.setUserName(vo.getUserName());
        userPojo.setNote(vo.getNote());
        return userPojo;
    }
}
