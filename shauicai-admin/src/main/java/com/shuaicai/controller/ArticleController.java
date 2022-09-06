package com.shuaicai.controller;

import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.dto.AddArticleDto;
import com.shuaicai.service.ArticleService;
import com.shuaicai.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //写博文
    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto articleDto){

        return articleService.add(articleDto);
    }


}
