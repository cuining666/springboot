package com.springboot.chapter9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
@RequestMapping("/i18n")
public class I18nController {

    @Autowired
    private MessageSource messageSource;

    /**
     * 获取国际化信息和打开国际化页面
     * @return
     */
    @GetMapping("/page")
    public String page() {
        // 后台获取国际化区域
        Locale locale = LocaleContextHolder.getLocale();
        // 获取国际化消息
        String msg = messageSource.getMessage("msg", null, locale);
        System.out.println("msg = " + msg);
        // 返回视图
        return "i18n/internationalization";
    }
}
