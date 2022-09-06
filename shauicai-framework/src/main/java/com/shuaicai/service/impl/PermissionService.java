package com.shuaicai.service.impl;

import com.shuaicai.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PermissionService
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/9/6 14:07
 * @PackagePath com.shuaicai.service.impl
 * @Version 1.0
 */
@Service("ps")
public class PermissionService {
    /**
     *  判断当前用户是否具有Permission权限
     * @param permission 要判断的权限
     * @return
     */
    public boolean hasPermissions(String permission){
        //如果是超级管理员直接返回true
        if (SecurityUtils.isAdmin()){
            return true;
        }
        //否则 获取当前登录用户是否有该权限
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }

}
