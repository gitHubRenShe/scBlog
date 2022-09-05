package com.shuaicai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuaicai.domain.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * (Menu)表服务接口
 *
 * @author makejava
 * @since 2022-08-25 22:06:56
 */

public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}

