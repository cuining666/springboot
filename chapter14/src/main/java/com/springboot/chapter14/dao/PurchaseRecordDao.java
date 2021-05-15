package com.springboot.chapter14.dao;

import com.springboot.chapter14.pojo.PurchaseRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseRecord record);

    int insertSelective(PurchaseRecord record);

    PurchaseRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseRecord record);

    int updateByPrimaryKey(PurchaseRecord record);
}