package com.shuaicai.service.impl;

import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.entity.LoginUser;
import com.shuaicai.domain.entity.User;
import com.shuaicai.domain.vo.BlogUserLoginVo;
import com.shuaicai.domain.vo.UserInfoVo;
import com.shuaicai.service.BlogLoginService;
import com.shuaicai.utils.BeanCopyUtils;
import com.shuaicai.utils.JwtUtil;
import com.shuaicai.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Objects;

/**
 * @ClassName BlogLoginServiceImpl
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/21 14:06
 * @PackagePath com.shuaicai.service.impl
 * @Version 1.0
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断认证是否通过
        if (Objects.isNull(authenticate)){
            new RuntimeException("登录失败");
        }

        //如果认证通过了，使用userid生成一个jwt 存入ResponseResult返回
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id);

        //把完整的信息存入redis userid作为key
        redisCache.setCacheObject("bloglogin:"+id,loginUser);

        //把token和userInfo封装返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logut() {
        //获取token 解析获取userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser longinUser = (LoginUser) authentication.getPrincipal();
        Long id = longinUser.getUser().getId();
        //删除redis的用户信息
        redisCache.deleteObject("bloglogin:"+id);
        return ResponseResult.okResult();
    }
}
