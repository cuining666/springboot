package com.springboot.chapter2.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("user")
@Data
public class User {
    @Value("1")
    private Long id;
    @Value("user_name_1")
    private String username;
    @Value("note_1")
    private String note;
}
