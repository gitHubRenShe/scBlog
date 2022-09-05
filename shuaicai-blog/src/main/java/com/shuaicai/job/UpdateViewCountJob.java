package com.shuaicai.job;

import com.shuaicai.constants.SystemConstants;
import com.shuaicai.domain.entity.Article;
import com.shuaicai.service.ArticleService;
import com.shuaicai.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName TestJob
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/24 20:36
 * @PackagePath com.shuaicai.job
 * @Version 1.0
 */
@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/50 * * * * ?")
    public void updateViewCount(){
        //获取redis的浏览量
        Map<String, Integer> cacheMap = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEWCOUNTMAP);
        //更新到数据库
        List<Article> collect = cacheMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        articleService.updateBatchById(collect);
    }
}
