package com.springboot.chapter15.vo;

import lombok.Data;

@Data
public class ResultVo {
    private Boolean success;
    private String message;

    public ResultVo() {
    }

    public ResultVo(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
