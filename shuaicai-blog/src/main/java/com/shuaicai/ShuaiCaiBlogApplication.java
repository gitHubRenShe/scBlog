package com.shuaicai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName ShuaiCaiBlogApplication
 * @Description TODO
 * @Author shuai cai
 * @Date 2022/8/20 17:50
 * @PackagePath com.shuaicai
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.shuaicai.mapper")
@EnableScheduling
@EnableSwagger2
public class ShuaiCaiBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShuaiCaiBlogApplication.class,args);
    }
}
