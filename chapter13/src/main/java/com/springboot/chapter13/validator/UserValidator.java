package com.springboot.chapter13.validator;

import com.springboot.chapter13.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    // 确定支持的验证类型
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    // 验证逻辑
    @Override
    public void validate(Object target, Errors errors) {
        if (target == null) {
            errors.rejectValue("", null, "用户不能为空");
            return;
        }
        User user = (User) target;
        if (StringUtils.isEmpty(user.getUserName())) {
            errors.rejectValue("userName", null, "用户名不能为空");
        }
    }
}
