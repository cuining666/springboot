package com.springboot.chapter9.advice.test;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Date;

@Controller
@RequestMapping("/advice")
public class AdivceController {

    @GetMapping("/test")
    // 因为日期格式被控制器通知限定， 所以无法再给出
    public String test(Date date, Model model) {
        // 从数据模型中获取数据
        System.out.println(model.getAttribute("project_name"));
        // 打印日期参数
        System.out.println(DateFormatUtils.format(date, "yyyy-MM-dd"));
        // 抛出异常，这样流转到控制器异常通知
        throw new RuntimeException("异常了，跳转到控制器通知的异常信息里");
    }
}
