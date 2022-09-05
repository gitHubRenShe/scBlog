package com.shuaicai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuaicai.domain.entity.Role;

import java.util.List;


/**
 * (Role)表服务接口
 *
 * @author makejava
 * @since 2022-08-25 22:15:57
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}

