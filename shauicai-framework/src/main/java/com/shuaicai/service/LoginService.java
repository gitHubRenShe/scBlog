package com.shuaicai.service;

import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.entity.User;

/**
 * @ClassName LoginService
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/25 21:09
 * @PackagePath com.shuaicai.service
 * @Version 1.0
 */
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
