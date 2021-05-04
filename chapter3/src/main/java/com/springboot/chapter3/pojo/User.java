package com.springboot.chapter3.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class User {
    private long id;
    private String username;
    private String note;
}
