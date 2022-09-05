package com.shuaicai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.entity.Link;


/**
 * (Link)表服务接口
 *
 * @author makejava
 * @since 2022-08-21 13:23:53
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

