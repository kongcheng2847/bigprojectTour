package com.hqj.bigproject.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
        //记录Http请求
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //url
        LOG.info("url={}",request.getRequestURL());
        //ip
        LOG.info("ip={}",request.getRemoteAddr());
        //method
        LOG.info("method={}",request.getMethod());
        //class function
        LOG.info("class_function={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        //params
        LOG.info("params={}",joinPoint.getArgs());
        // 接收到请求，记录请求内容
        LOG.info("doBefore");
    }

//    @AfterReturning(returning = "ret", pointcut = "performance()")
//    public void doAfterReturning(Object ret) throws Throwable {
//        // 处理完请求，返回内容
//    	log.info("doAfterReturning");
//    }
    /*@AfterReturning(returning = "object",pointcut = "performance()")
    public void doAfterReturning(Object object){
        LOG.info("response={}",object.toString());
    }*/
}
