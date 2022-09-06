package com.shuaicai.controller;

import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.dto.TagListDto;
import com.shuaicai.domain.entity.Tag;
import com.shuaicai.domain.vo.PageVo;
import com.shuaicai.domain.vo.TagIdVo;
import com.shuaicai.enums.AppHttpCodeEnum;
import com.shuaicai.exception.SystemException;
import com.shuaicai.service.TagService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName TagController
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/25 20:45
 * @PackagePath com.shuaicai.controller
 * @Version 1.0
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){

        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping
    public ResponseResult insertTag(@RequestBody Tag tag){

        return tagService.insertTag(tag);
    }

    //删除标签
    @DeleteMapping("{id}")
    public ResponseResult deleteId(@PathVariable("id") Long id){
        if (id == null){
            throw new SystemException(AppHttpCodeEnum.TAGID_NOT_NULL);
        }
        tagService.removeById(id);

        return ResponseResult.okResult();
    }

    //修改标签
    @GetMapping("{id}")
    public ResponseResult getId(@PathVariable("id") Long id){

        return tagService.getTagId(id);
    }
    @PutMapping
    public ResponseResult updateTagId(@RequestBody TagIdVo tagIdVo){

        return tagService.updateTagId(tagIdVo);
    }

    //查询所有标签接口
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }
}
