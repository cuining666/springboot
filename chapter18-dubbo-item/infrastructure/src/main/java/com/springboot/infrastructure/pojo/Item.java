package com.springboot.infrastructure.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("item")
public class Item {
    private Integer id;

    private String name;

    private Integer stock;
}