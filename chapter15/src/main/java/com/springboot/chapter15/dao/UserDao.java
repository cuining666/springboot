package com.springboot.chapter15.dao;

import com.springboot.chapter15.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectByUserNameAndNote(String userName, String note, int start, int limit);

    int updateUserNameById(Long id, String userName);
}