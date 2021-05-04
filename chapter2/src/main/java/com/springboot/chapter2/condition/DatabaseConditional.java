package com.springboot.chapter2.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class DatabaseConditional implements Condition {
    /**
     * 数据库装配条件
     * @param conditionContext 条件上下文
     * @param annotatedTypeMetadata 注释类型的元数据
     * @return true装配Bean，否则不装配
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        // 取出环境变量
        Environment environment = conditionContext.getEnvironment();
        // 判断属性文件是否存在对应的数据库配置
        return environment.containsProperty("database.driverName")
                && environment.containsProperty("database.url")
                && environment.containsProperty("database.username")
                && environment.containsProperty("database.password");
    }
}
