package com.springboot.chapter14.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Alias("purchaseRecord")
public class PurchaseRecord implements Serializable {

    private static final long serialVersionUID = -4230971886819977380L;

    private Long id;

    private Long userId;

    private Long productId;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal sum;

    private Date purchaseDate;

}