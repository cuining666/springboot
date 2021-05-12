package com.springboot.chapter13.pojo;

import lombok.Data;

@Data
public class UserPojo {
    private Long id;
    private String userName;
    // 1-男，2-女
    private int sex;
    private String note;
}
