package com.springboot.chapter9.controller;

import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/my")
public class MyController {

    /**
     * 在无注解下获取参数，要求参数名称和Http请求参数名称一致
     * http://localhost:8080/my/no/annotation?intVal=1&longVa1=2&str=str
     * @param intVal
     * @param longVal
     * @param str
     * @return json参数
     */
    @GetMapping("/no/annotation")
    @ResponseBody
    public Map<String, Object> noAnnotation(Integer intVal, Long longVal, String str) {
        Map<String, Object> map = new HashMap<>();
        map.put("intVal", intVal);
        map.put("longVal", longVal);
        map.put("str", str);
        return map;
    }

    /**
     * 通过注解@RequestParam获取参数
     * http://localhost:8080/my/annotation?int_val=1&long_va1=2&str_val=str
     * @param intVal
     * @param longVal
     * @param str
     * @return json参数
     */
    @GetMapping("/annotation")
    @ResponseBody
    public Map<String, Object> requetParam(@RequestParam(value = "int_val", required = false) Integer intVal,
                                           @RequestParam(value = "long_val", required = false) Long longVal,
                                           @RequestParam(value = "str_val", required = false) String str) {
        Map<String, Object> map = new HashMap<>();
        map.put("intVal", intVal);
        map.put("longVal", longVal);
        map.put("str", str);
        return map;
    }

    /**
     * 传递数组
     * http://localhost:8080/my/requestArray?intArr=1,2,3&longArr=4,5,6&strArr=str1,str2,str3
     * @param intArr
     * @param longArr
     * @param strArr
     * @return
     */
    @GetMapping("/requestArray")
    @ResponseBody
    public Map<String, Object> requestArray(int[] intArr, Long[] longArr, String[] strArr) {
        Map<String, Object> map = new HashMap<>();
        map.put("intArr", intArr);
        map.put("longArr", longArr);
        map.put("strArr", strArr);
        return map;
    }
}
