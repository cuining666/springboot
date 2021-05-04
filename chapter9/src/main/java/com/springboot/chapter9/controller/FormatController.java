package com.springboot.chapter9.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/format")
public class FormatController {

    /**
     * http://localhost:8080/format/form
     * @return
     */
    @GetMapping("/form")
    public String showFormat() {
        return "format/formatter";
    }

    /**
     * 获取提交参数
     * @param date
     * @param number
     * @return
     */
    @PostMapping("/commit")
    @ResponseBody
    // 注解@DateTimeFormat和＠NumberFormat，它们配置了格式化所约定的格式，所以Spring会根据约定的格式把数据转换出来，这样就可以完成参数的转换
    public Map<String, Object> format(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, @NumberFormat(pattern = "#,###.##") Double number) {
        Map<String, Object> map = new HashMap<>();
        map.put("date", date);
        map.put("number", number);
        return map;
    }
}
