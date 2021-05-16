package com.springboot.chapter15.pojo;

import com.springboot.chapter15.enumeration.SexEnum;
import lombok.Data;

@Data
public class User {
    private Long id;

    private String userName;

    private SexEnum sex;

    private String note;

}