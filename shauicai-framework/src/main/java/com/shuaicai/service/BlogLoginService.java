package com.shuaicai.service;

import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.entity.User;

/**
 * @ClassName BlogLoginService
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/21 14:05
 * @PackagePath com.shuaicai.service
 * @Version 1.0
 */
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logut();
}
