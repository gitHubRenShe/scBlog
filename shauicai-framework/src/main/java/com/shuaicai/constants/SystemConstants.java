package com.shuaicai.constants;

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     *  状态正常
     */
    public static final String STATUS_NORMAL = "0";

    /**
     *  友联审核状态为通过
     */
    public static final String lINK_STATUS_NORMAL = "0";

    /**
     *  根评论为-1
     */
    public static final String Comment_STATUS_NORMAL = "-1";
    /**
     *  0为文章类型评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     *  1为友链评论
     */
    public static final String link_COMMENT = "1";
    /**
     * 存储到redis的浏览量key名
     */
    public static final String ARTICLE_VIEWCOUNTMAP = "article:viewCount";
    /**
     *  类型为菜单
     */
    public static final String MENU = "C";
    /**
     *  类型为按钮
     */
    public static final String BUTTON = "F";

    /** 正常状态 */
    public static final String NORMAL = "0";
}