package com.springboot.chapter13.vo;

import lombok.Data;

@Data
public class UserVo {
    private Long id;
    private String userName;
    private int sexCode;
    private String sexName;
    private String note;
}
