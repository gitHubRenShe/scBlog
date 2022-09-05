package com.shuaicai.runner;

import com.shuaicai.constants.SystemConstants;
import com.shuaicai.domain.entity.Article;
import com.shuaicai.mapper.ArticleMapper;
import com.shuaicai.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName TestRunner
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/24 20:29
 * @PackagePath com.shuaicai.runner
 * @Version 1.0
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息 id viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));

        //存储到redis中
        redisCache.setCacheMap(SystemConstants.ARTICLE_VIEWCOUNTMAP,viewCountMap);
        System.out.println("已经初始化！！！！！！！！！！！！！！");
    }
}
