package com.shuaicai.controller;

import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.entity.LoginUser;
import com.shuaicai.domain.entity.Menu;
import com.shuaicai.domain.entity.User;
import com.shuaicai.domain.vo.AdminUserInfoVo;
import com.shuaicai.domain.vo.RoutersVo;
import com.shuaicai.domain.vo.UserInfoVo;
import com.shuaicai.enums.AppHttpCodeEnum;
import com.shuaicai.exception.SystemException;
import com.shuaicai.service.LoginService;
import com.shuaicai.service.MenuService;
import com.shuaicai.service.RoleService;
import com.shuaicai.utils.BeanCopyUtils;
import com.shuaicai.utils.RedisCache;
import com.shuaicai.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/25 21:08
 * @PackagePath com.shuaicai.controller
 * @Version 1.0
 */
@RestController
@ResponseBody
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = loginUser.getUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(user.getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(user.getId());

        //封装数据返回

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        AdminUserInfoVo adminUserInfo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfo);
    }

    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        //查询menu 结果返回tree的形式
        Long userId = SecurityUtils.getUserId();
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }
    @PostMapping("/user/logout")
    public ResponseResult logout(){

        return loginService.logout();
    }
}
