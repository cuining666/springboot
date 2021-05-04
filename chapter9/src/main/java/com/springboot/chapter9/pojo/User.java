package com.springboot.chapter9.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("user")
public class User {
    private Long id;

    private String userName;

    private Integer sex;

    private String note;

}