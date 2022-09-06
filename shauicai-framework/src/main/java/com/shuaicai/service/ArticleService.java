package com.shuaicai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.dto.AddArticleDto;
import com.shuaicai.domain.entity.Article;
import org.springframework.stereotype.Service;

/**
 * @ClassName ArticleService
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/20 20:21
 * @PackagePath com.shuaicai.service
 * @Version 1.0
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);
    //写博文
    ResponseResult add(AddArticleDto articleDto);

}
