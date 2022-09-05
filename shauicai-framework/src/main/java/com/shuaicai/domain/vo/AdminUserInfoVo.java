package com.shuaicai.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName AdminUserInfoVo
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/25 22:22
 * @PackagePath com.shuaicai.domain.vo
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {

    private List<String> permissions;

    private List<String> roles;

    private UserInfoVo user;
}