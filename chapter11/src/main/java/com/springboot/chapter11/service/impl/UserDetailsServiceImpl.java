package com.springboot.chapter11.service.impl;

import com.springboot.chapter11.pojo.Role;
import com.springboot.chapter11.pojo.User;
import com.springboot.chapter11.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义用户认证服务
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRoleService userRoleService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 获取用户信息
        User user = userRoleService.getUserByUserName(userName);
        // 获取角色信息
        List<Role> roleList = userRoleService.findRolesByUserName(userName);
        return changeToUser(user, roleList);
    }

    private UserDetails changeToUser(User user, List<Role> roleList) {
        // 权限列表
        List<GrantedAuthority> authorityList = new ArrayList<>();
        // 赋予查询到的角色
        for (Role role : roleList) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
            authorityList.add(authority);
        }
        // 创建UserDetails对象，设置用户名、密码和权限
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUserName(), "{noop}" + user.getPwd(), authorityList);
        return userDetails;
    }

}
