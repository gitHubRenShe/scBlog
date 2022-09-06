package com.shuaicai.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName ArticleDetailVo
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/21 12:56
 * @PackagePath com.shuaicai.domain.vo
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {
    private Long id;
    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 所属分类id
     */
    private Long categoryId;
    /**
     * 所属分类名字
     */
    private String categoryName;
    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 访问量
     */
    private Long viewCount;

    private Date createTime;
}
