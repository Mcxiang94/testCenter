package com.CcDev;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Cui
 * @date 2022/2/17 - 14:59
 * @email yuxiangcui6@gmail.com
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.CcDev.mapper")
@EnableTransactionManagement
public class Starter {
    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }
}
