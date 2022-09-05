package com.shuaicai.service;

import com.shuaicai.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UploadSerivce
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/23 19:18
 * @PackagePath com.shuaicai.service
 * @Version 1.0
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
