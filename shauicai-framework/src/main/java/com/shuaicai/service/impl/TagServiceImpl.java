package com.shuaicai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.dto.TagListDto;
import com.shuaicai.domain.entity.Tag;
import com.shuaicai.domain.vo.PageVo;
import com.shuaicai.domain.vo.TagIdVo;
import com.shuaicai.domain.vo.TagVo;
import com.shuaicai.enums.AppHttpCodeEnum;
import com.shuaicai.exception.SystemException;
import com.shuaicai.mapper.TagMapper;
import com.shuaicai.service.TagService;
import com.shuaicai.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * (SgTag)表服务实现类
 *
 * @author makejava
 * @since 2022-08-25 20:42:37
 */
@Service("sgTagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        lambdaQueryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        lambdaQueryWrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        Page<Tag> tagPage = new Page<>();
        tagPage.setCurrent(pageNum);
        tagPage.setSize(pageSize);
        page(tagPage,lambdaQueryWrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(tagPage.getRecords(),tagPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult insertTag(Tag tag) {
        //创建Tag对象添加
        if (!StringUtils.hasText(tag.getName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        tag.setName(tag.getName());
        tag.setRemark(tag.getRemark());

        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //只查询id和name
        lambdaQueryWrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(lambdaQueryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }

    @Override
    public ResponseResult getTagId(Long id) {

        Tag tag = getById(id);
        if (tag == null){
            throw new SystemException(AppHttpCodeEnum.OBJEKT_NOT_NULL);
        }
        TagIdVo tagIdVo = BeanCopyUtils.copyBean(tag, TagIdVo.class);
        return ResponseResult.okResult(tagIdVo);
    }

    @Override
    public ResponseResult updateTagId(TagIdVo tagIdVo) {
        //将tagIdVo拷贝到Tag标签实体类
        Tag tag = BeanCopyUtils.copyBean(tagIdVo, Tag.class);
        updateById(tag);

        return ResponseResult.okResult();
    }

    //删除标签
    @Override
    public ResponseResult deleteTagId(Long id) {
        Tag tag = getById(id);
        if (tag == null){
            throw new SystemException(AppHttpCodeEnum.OBJEKT_NOT_NULL);
        }
        removeById(id);
        return ResponseResult.okResult();
    }
}

