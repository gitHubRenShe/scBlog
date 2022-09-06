package com.shuaicai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shuaicai.constants.SystemConstants;
import com.shuaicai.domain.entity.LoginUser;
import com.shuaicai.domain.entity.User;
import com.shuaicai.mapper.MenuMapper;
import com.shuaicai.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName UserDetailsServiceImpl
 * @Description
 * @Author shuai cai
 * @Date 2022/8/21 14:23
 * @PackagePath com.shuaicai.service.impl
 * @Version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);

        User user = userMapper.selectOne(queryWrapper);
        //如果没有查询到
        if (Objects.isNull(user)){
            throw new RuntimeException("用户名不存在");
        }

        //TODO 查询权限信息，如果是后台用户才需要权限封装
        if (user.getType().equals(SystemConstants.ADMIN)){
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,list);
        }

        return new LoginUser(user);
    }
}
