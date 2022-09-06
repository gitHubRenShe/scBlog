package com.shuaicai.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.entity.Category;
import com.shuaicai.domain.vo.CategoryVo;
import com.shuaicai.domain.vo.ExcelCategoryVo;
import com.shuaicai.enums.AppHttpCodeEnum;
import com.shuaicai.service.CategoryService;
import com.shuaicai.utils.BeanCopyUtils;
import com.shuaicai.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @ClassName CategoryController
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/9/5 9:53
 * @PackagePath com.shuaicai.controller
 * @Version 1.0
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){

        //查询所有分类接口
        return categoryService.listAllCategory();
    }

    //导出Excel
    @GetMapping("/export")
    @PreAuthorize("@ps.hasPermissions('content:category:export')")
    public void export(HttpServletResponse response){
        //设置下载文件的响应头
        try {
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categories = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categories, ExcelCategoryVo.class);
            //把数据写到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
//            e.printStackTrace();
            //出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response,JSON.toJSONString(result));

        }


    }
}
