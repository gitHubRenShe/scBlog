package com.shuaicai.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CategoryVo
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/20 23:29
 * @PackagePath com.shuaicai.domain.vo
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {

    private Long id;
    private String name;

    //描述
    private String description;
}
