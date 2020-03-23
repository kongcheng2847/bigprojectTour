package com.hqj.bigproject.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogServiceTakeTime {
    private static final Logger LOG = LoggerFactory.getLogger(LogServiceTakeTime.class);
    @Pointcut("execution(* com.hqj.bigproject.service..*.*(..))")
    public void performance(){
    }

    @Around("performance()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {

        //记录起始时间
        long begin = System.currentTimeMillis();
        Object result = "";
        /** 执行目标方法 */
        try{
            result= joinPoint.proceed();
        }
        catch(Exception e){
            LOG.error("日志记录发生错误, errorMessage: {}", e.getMessage());
        }
        finally{
            /** 记录操作时间 */
            long took = (System.currentTimeMillis() - begin)/1000;
            if (took >= 10) {
                LOG.error("Service 执行时间为: {}秒", took);
            } else if (took >= 5) {
                LOG.warn("Service 执行时间为: {}秒", took);
            } else  if (took >= 2) {
                LOG.info("Service执行时间为: {}秒", took);
            }
        }
        return result;
    }

    @Before("performance()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        LOG.info("doBefore");
    }

//    @AfterReturning(returning = "ret", pointcut = "performance()")
//    public void doAfterReturning(Object ret) throws Throwable {
//        // 处理完请求，返回内容
//    	log.info("doAfterReturning");
//    }
}
