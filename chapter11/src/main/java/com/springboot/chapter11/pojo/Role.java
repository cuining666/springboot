package com.springboot.chapter11.pojo;

import lombok.Data;

@Data
public class Role {
    private Integer id;

    private String roleName;

    private String note;
}