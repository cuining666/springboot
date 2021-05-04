package com.springboot.chapter10.advice;

import com.springboot.chapter10.exception.NotFoundException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

// 控制器通知
// 指定拦截包的控制器，限定被标注为@Controller和@RestController的类才被拦截
@ControllerAdvice(basePackages = {"com.springboot.chapter10.controller.*"}, annotations = {Controller.class, RestController.class})
public class UserControllerAdvice {

    // 异常处理，可以定义异常类型进行拦截处理
    @ExceptionHandler(value = NotFoundException.class)
    // 以JSON表达式方式响应
    @ResponseBody
    // 定义为服务器错误状态码
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> exception(HttpServletRequest request, NotFoundException exception) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", exception.getCode());
        result.put("message", exception.getCustomMsg());
        return result;
    }
}
