package com.hqj.bigproject.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务测试类
 */
@Component//组件 可被容器扫描
public class TestTask {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    //定义每3秒执行任务
    //@Scheduled(fixedRate = 3000)//固定
    @Scheduled(cron = "15-35 * * * * ? ")//cron 表达式
    public void reportCurrentTime(){
        System.out.println("现在时间："+DATE_FORMAT.format(new Date()));
    }
}
