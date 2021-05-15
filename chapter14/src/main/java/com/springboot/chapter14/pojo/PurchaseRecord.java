package com.springboot.chapter14.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Alias("purchaseRecord")
public class PurchaseRecord {
    private Long id;

    private Long userId;

    private Long productId;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal sum;

    private Date purchaseDate;

}