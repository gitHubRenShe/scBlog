package com.shuaicai.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TagListDto
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/26 22:14
 * @PackagePath com.shuaicai.domain.dto
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {
    private String name;
    private String remark;
}
