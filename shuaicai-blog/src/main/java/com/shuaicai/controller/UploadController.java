package com.shuaicai.controller;

import com.shuaicai.domain.ResponseResult;
import com.shuaicai.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UploadController
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/23 19:12
 * @PackagePath com.shuaicai.controller
 * @Version 1.0
 */
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){

        return uploadService.uploadImg(img);
    }
}
