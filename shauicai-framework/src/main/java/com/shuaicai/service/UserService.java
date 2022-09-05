package com.shuaicai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.entity.User;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-08-22 18:52:16
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}

