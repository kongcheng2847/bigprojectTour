package com.hqj.bigproject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.hqj.bigproject.mapper")
//@ComponentScan(basePackages = "com")
//@EnableScheduling//开启定时任务
@EnableAsync//开启异步任务 自动扫描
public class BigprojectTourApplication {
    private static final Logger LOG = LoggerFactory.getLogger(BigprojectTourApplication.class);
    public static void main(String[] args) {
        LOG.info("万丈红尘三杯酒,千秋大业一壶茶...");
        SpringApplication.run(BigprojectTourApplication.class, args);
        LOG.info("项目运行正常...");
    }

}
