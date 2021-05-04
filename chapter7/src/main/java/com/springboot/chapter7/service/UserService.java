package com.springboot.chapter7.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.springboot.chapter7.pojo.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    List<User> findUsers(String userName, String note, int skip, int limit);

    UpdateResult updateUser(Long id, String userName, String note);

    User getUser(Long id);

    DeleteResult deleteUser(Long id);
}
