package com.springboot.chapter7.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Data
// 标识为MongoDB文档
@Document
public class User implements Serializable {

    private static final long serialVersionUID = -996931499295334231L;

    // MongoDB文档编号，主键
    @Id
    private Long id;
    @Field("user_name")
    private String userName;
    private String note;
    // 角色列表
    private List<Role> roles;
}
