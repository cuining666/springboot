package com.springboot.chapter2.pojo;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
// Bean的作用域
//singleton 所有Spring应用 默认值， loC 容器只存在单例
//prototype 所有Spring应用 每当从IoC 容器中取出一个Bean ，则创建一个新的Bean
//session Spring Web应用 HTTP会话
//application Spring Web应用 Web工程生命周期
//request Spring Web应用 Web工程单次请求（request)
//global Session Spring Web应用 在一个全局的HTTP Session中，一个Bean定义对应一个实例。实践中基本不使用
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScopeBean {
}
