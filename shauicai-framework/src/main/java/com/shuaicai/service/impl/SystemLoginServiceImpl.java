package com.shuaicai.service.impl;

import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.entity.LoginUser;
import com.shuaicai.domain.entity.User;
import com.shuaicai.service.LoginService;
import com.shuaicai.utils.JwtUtil;
import com.shuaicai.utils.RedisCache;
import com.shuaicai.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName LoginServiceImpl
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/25 21:09
 * @PackagePath com.shuaicai.service.impl
 * @Version 1.0
 */
@Service
public class SystemLoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);

        //把token封装 返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //删除redis的token值
        Long userId = SecurityUtils.getUserId();
        redisCache.deleteObject("login:"+userId);

        return ResponseResult.okResult();
    }
}
