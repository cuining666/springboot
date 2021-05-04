package com.springboot.chapter11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/csrf")
    public String csrfPage() {
        return "csrf_form";
    }

    @PostMapping("/commit")
    @ResponseBody
    public void csrf() {

    }
}
