package com.springboot.chapter10.exception;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {

    private static final long SerialVersionUID = 1L;
    private Long code;
    private String customMsg;

    public NotFoundException(Long code, String customMsg) {
        super();
        this.code = code;
        this.customMsg = customMsg;
    }
}
