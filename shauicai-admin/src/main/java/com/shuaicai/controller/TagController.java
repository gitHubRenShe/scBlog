package com.shuaicai.controller;

import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.dto.TagListDto;
import com.shuaicai.domain.entity.Tag;
import com.shuaicai.domain.vo.PageVo;
import com.shuaicai.service.TagService;
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

    //查询所有标签接口
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }
}
