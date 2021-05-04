package com.springboot.chapter4.service;

import com.springboot.chapter4.pojo.User;

import java.util.List;

public interface JdbcTmplUserService {
    User getUserById(Long id);

    /**
     * 使用StatementCallback执行多条sql
     * @param id
     * @return
     */
    User getUserById2(Long id);

    /**
     * 使用ConnectionCallback执行多条sql
     * @param id
     * @return
     */
    User getUserById3(Long id);
    List<User> findUsers(String userName, String note);
    int insertUser(User user);
    int updateUser(User user);
    int deleteUserById(Long id);
}
