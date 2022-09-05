package com.shuaicai.controller;

import com.shuaicai.domain.ResponseResult;
import com.shuaicai.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LinkController
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/21 13:21
 * @PackagePath com.shuaicai.controller
 * @Version 1.0
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }

}
