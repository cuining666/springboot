package com.springcloud.chapter16.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPo implements Serializable {
    private static final long serialVersionUID = -3086472705135265715L;
    private Long id;
    private String userName;
    // 1白银会员，2黄金会员，3钻石会员
    private int level;
    private String note;
}
