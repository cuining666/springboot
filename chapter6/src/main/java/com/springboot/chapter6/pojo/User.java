package com.springboot.chapter6.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Alias("user")
public class User implements Serializable {

    private static final long serialVersionUID = -8573511088032525294L;
    private Long id;

    private String userName;

    private Integer sex;

    private String note;

}