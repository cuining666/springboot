package com.springboot.chapter12.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 6770383322383487109L;
    private Long id;
    private String userName;
    private String note;

    public User(Long id, String userName, String note) {
        this.id = id;
        this.userName = userName;
        this.note = note;
    }
}
