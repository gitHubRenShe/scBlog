package com.shuaicai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuaicai.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-09 22:32:07
 */
public interface MenuMapper extends BaseMapper<Menu> {
    //查询权限集合
    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}

