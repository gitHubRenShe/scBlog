package com.shuaicai.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName HotArticleVo
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/20 21:36
 * @PackagePath com.shuaicai.domain.vo
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVo {

    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 访问量
     */
    private Long viewCount;
}
