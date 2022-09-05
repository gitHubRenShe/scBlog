package com.shuaicai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.dto.TagListDto;
import com.shuaicai.domain.entity.Tag;


/**
 * (SgTag)表服务接口
 *
 * @author makejava
 * @since 2022-08-25 20:42:37
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);


    ResponseResult insertTag(Tag tag);

    ResponseResult listAllTag();
}

