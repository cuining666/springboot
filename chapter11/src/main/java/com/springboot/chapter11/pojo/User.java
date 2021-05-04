package com.springboot.chapter11.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String userName;

    private String pwd;

    private Integer available;

    private String note;
}