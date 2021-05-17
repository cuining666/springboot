package com.springcloud.chapter16.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;

@Component
public class MyZuulFilter extends ZuulFilter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 过滤类型为请求前
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器排序，数字越小优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        // 请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取HttpServletRequest对象
        HttpServletRequest request = ctx.getRequest();
        // 取出表单序列号
        String serialNumber = request.getParameter("serialNumber");
        // 如果存在验证码，则返回true，启用过滤器
        return !StringUtils.isEmpty(serialNumber);
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String serialNumber = request.getParameter("serialNumber");
        // 取出表单序列号和请求验证码
        String reqCode = request.getParameter("verificationCode");
        // 从Redis中取出验证码
        String verifyCode = redisTemplate.opsForValue().get(serialNumber);
        // Redis验证码为空或者与请求不一致，拦截请求报出错误
        if (verifyCode == null || !verifyCode.equals(reqCode)) {
            // 不再转发请求
            ctx.setSendZuulResponse(false);
            // 设置HTTP响应码为401（未授权）
            ctx.setResponseStatusCode(401);
            // 设置响应类型为JSON数据集
            ctx.getResponse().setContentType(MediaType.APPLICATION_JSON.getType());
            // 设置响应体
            ctx.setResponseBody("{'success':false,'message':'Verification Code Error'}");
        }
        // 一致放过
        return null;
    }
}
