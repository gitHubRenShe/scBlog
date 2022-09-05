package com.shuaicai.utils;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName BeanCopyUtils
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/20 22:05
 * @PackagePath com.shuaicai.utils
 * @Version 1.0
 */
public class BeanCopyUtils {

    //拷贝单个对象
    public static <V>V copyBean(Object source,Class<V> clazz){
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }

    //拷贝List集合
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o,clazz))
                .collect(Collectors.toList());
    }


}
