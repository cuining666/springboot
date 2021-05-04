package com.springboot.chapter7.service.impl;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.springboot.chapter7.pojo.User;
import com.springboot.chapter7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    // 注入MongoTemplate对象
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveUser(User user) {
        mongoTemplate.save(user);
    }

    @Override
    public List<User> findUsers(String userName, String note, int skip, int limit) {
        // 将用户名和备注设置为模糊查询准则
        Criteria criteria = Criteria.where("userName").regex(userName).and("note").regex(note);
        // 构建查询条件，并设置分页跳过前skip个，至多返回limit个
        Query query = Query.query(criteria).limit(limit).skip(skip);
        // 执行
        List<User> userList = mongoTemplate.find(query, User.class);
        return userList;
    }

    @Override
    public UpdateResult updateUser(Long id, String userName, String note) {
        // 确定要更新的对象
        Criteria criteria = Criteria.where("id").is(id);
        Query query = Query.query(criteria);
        // 定义更新对象，后续可变的字符串代表排除在外的属性
        Update update = Update.update("userName", userName);
        update.set("note", note);
        // 更新第一个文档
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
        // 更新多个对象
//        UpdateResult result = mongoTemplate.updateMulti(query, update, User.class);
        return result;
    }

    @Override
    public User getUser(Long id) {
        return mongoTemplate.findById(id, User.class);
        // 如果只需要获得一个，也可以采用如下查询方法
//        Criteria criteria = Criteria.where("id").is(id);
//        Query query = Query.query(criteria);
//        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public DeleteResult deleteUser(Long id) {
        // 构建id相等的条件
        Criteria criteria = Criteria.where("id").is(id);
        // 查询对象
        Query query = Query.query(criteria);
        // 删除用户
        DeleteResult result = mongoTemplate.remove(query, User.class);
        return result;
    }
}
