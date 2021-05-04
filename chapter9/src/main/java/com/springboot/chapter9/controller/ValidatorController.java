package com.springboot.chapter9.controller;

import com.springboot.chapter9.pojo.ValidatorPojo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/valid")
public class ValidatorController {

    @GetMapping("/page")
    public String validPage() {
        return "validator/pojo";
    }

    /**
     * 解析验证参数错误
     * @param vp 需要验证的POJO，使用注解＠Valid表示验证
     * @param errors 错误信息，它由Spring MVC通过验证POJO后自动填充
     * @return 错误信息Map
     */
    @PostMapping("/validate")
    @ResponseBody
    public Map<String, Object> validate(@Valid @RequestBody ValidatorPojo vp, Errors errors) {
        Map<String, Object> errMap = new HashMap<>();
        // 获取错误列表
        List<ObjectError> oes = errors.getAllErrors();
        for (ObjectError oe : oes) {
            String key = null;
            String msg = null;
            // 字段错误
            if (oe instanceof FieldError) {
                FieldError fe = (FieldError) oe;
                // 获取错误验证字段名
                key = fe.getField();
            } else {
                // 非字段错误
                key = oe.getObjectName();
            }
            // 错误消息
            msg = oe.getDefaultMessage();
            errMap.put(key, msg);
        }
        return errMap;
    }
}
