package com.springboot.chapter13.pojo;

import com.springboot.chapter13.enumeration.SexEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

// 标识为MongoDB文档
@Document
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -2659792130462735136L;
    @Id
    private Long id;
    private SexEnum sex;
    @Field("user_name")
    private String userName;
    private String note;
}
