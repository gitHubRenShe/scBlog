package com.shuaicai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.entity.Category;
import com.shuaicai.domain.vo.CategoryVo;

import java.util.List;

/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2022-08-20 22:44:14
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();
}

