package com.hqj.bigproject.config;

import com.hqj.bigproject.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        //所有路径都被拦截
        registration.addPathPatterns("/**");
        //添加不拦截路径 登录  html静态资源  js静态资源
        String[] arr = {"/bigproject/login.do","/user/createuser","/user/checklogin","/**/*.html","/**/*.js","/**/*.css","/static/**"};
        registration.excludePathPatterns("/bigproject/login.do","/user/createuser","/user/checklogin","/**/*.html","/**/*.js","/**/*.css","/static/**");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //需要配置1：需要告知系统，这是要被当成静态文件的！
        //第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        //super.addResourceHandlers(registry);
    }
}
