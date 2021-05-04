package com.springboot.chapter9.coverter;

import com.springboot.chapter9.pojo.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 自定义字符串用户转换器
 */
@Component
public class StringToUserConverter implements Converter<String, User> {
    @Override
    public User convert(String userStr) {
        User user = new User();
        String[] strArr = userStr.split("-");
        Long id = Long.parseLong(strArr[0]);
        String userName = strArr[1];
        String note = strArr[2];
        user.setId(id);
        user.setUserName(userName);
        user.setNote(note);
        return user;
    }
}
