package com.springboot.chapter7.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Document
public class Role implements Serializable {

    private static final long serialVersionUID = 8131558977500204484L;
    private Long id;
    @Field("role_name")
    private String roleName;
    private String note;
}
