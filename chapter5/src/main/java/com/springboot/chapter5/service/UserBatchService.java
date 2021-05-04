package com.springboot.chapter5.service;

import com.springboot.chapter5.pojo.User;
import java.util.List;

public interface UserBatchService {
    int insertUsers(List<User> userList);
}
