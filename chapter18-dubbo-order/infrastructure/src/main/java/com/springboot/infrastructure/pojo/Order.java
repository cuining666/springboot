package com.springboot.infrastructure.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("order")
public class Order {
    private Integer id;

    private String orderNum;

    private Integer itemId;
}