package com.shuaicai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuaicai.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-09 22:36:44
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}

