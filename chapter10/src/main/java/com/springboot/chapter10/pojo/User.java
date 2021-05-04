package com.springboot.chapter10.pojo;

import com.springboot.chapter10.enumeration.SexEnum;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("user")
public class User {
    private Long id;

    private String userName;

    private SexEnum sex;

    private String note;

}