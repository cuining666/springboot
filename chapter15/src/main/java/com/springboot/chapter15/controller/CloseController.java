package com.springboot.chapter15.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
public class CloseController {

    @GetMapping("/close")
    public ModelAndView closePage() {
        ModelAndView mv = new ModelAndView("close");
        return mv;
    }
}
