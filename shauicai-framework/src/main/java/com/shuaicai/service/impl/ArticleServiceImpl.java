package com.shuaicai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaicai.constants.SystemConstants;
import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.dto.AddArticleDto;
import com.shuaicai.domain.entity.Article;
import com.shuaicai.domain.entity.ArticleTag;
import com.shuaicai.domain.entity.Category;
import com.shuaicai.domain.vo.ArticleDetailVo;
import com.shuaicai.domain.vo.ArticleListVo;
import com.shuaicai.domain.vo.PageVo;
import com.shuaicai.mapper.ArticleMapper;
import com.shuaicai.service.ArticleService;
import com.shuaicai.service.ArticleTagService;
import com.shuaicai.service.CategoryService;
import com.shuaicai.utils.BeanCopyUtils;
import com.shuaicai.domain.vo.HotArticleVo;
import com.shuaicai.utils.RedisCache;
import com.shuaicai.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName ArticleServiceImpl
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/20 20:22
 * @PackagePath com.shuaicai.service.impl
 * @Version 1.0
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;


    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //1.必须是正式文章（非草稿）
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //2.按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //3.最多显示10条
        Page<Article> articlePage = new Page<>(1, 10);
        page(articlePage, queryWrapper);

        List<Article> records = articlePage.getRecords();

        //Bean拷贝
//        List<HotArticleVo> hotArticleVos = new ArrayList<>();
//        for (Article record : records) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(record,vo);
//            hotArticleVos.add(vo);
//        }
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //如果有传categoryId就需要判断分类和categoryId相同才行
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);

        //状态要是正式发布的Status为0
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照isTop降序排列，来把置顶文章为1的排到上面
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> articlePage = new Page<>(pageNum, pageSize);
        page(articlePage, lambdaQueryWrapper);

        //查询category表的categoryName
        List<Article> articles = articlePage.getRecords();

        //根据articleId查询categoryName，封装到article的articleName里
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());


        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articlePage.getRecords(), ArticleListVo.class);
        //封装到PageVo中确保将来发给前端是前端所要格式
        PageVo pageVo = new PageVo(articleListVos, articlePage.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取
        Integer cacheMapValue = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEWCOUNTMAP, id.toString());
        article.setViewCount(cacheMapValue.longValue());

        //转换成vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if (category != null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装相应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.ARTICLE_VIEWCOUNTMAP,id.toString(),1L);

        return ResponseResult.okResult();
    }

    //写博文
    @Override
    @Transactional//声明式事务
    public ResponseResult add(AddArticleDto articleDto) {
        //将Dto拷贝到Article文章实体类
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        Long userId = SecurityUtils.getUserId();
        article.setCategoryId(userId);
        //添加文章
        save(article);

        //拿到ArticleTag的内容，文章id和标签id
        List<ArticleTag> tags = articleDto.getTags().stream()
                .map(aLong -> new ArticleTag(article.getId(), aLong))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(tags);

        return ResponseResult.okResult();
    }
}
