package com.hqj.bigproject.interceptor;

import com.hqj.bigproject.utils.JsonUtils;
import com.hqj.bigproject.utils.UserJSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class LoginInterceptor implements HandlerInterceptor {

    final static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

        Object user = request.getSession().getAttribute("loginUser");
        if(user == null){//未登录
            //String msg = "请先登录！";
            //returnErrorResponse(response,UserJSONResult.ok(msg));
            //页面重定向，返回登陆页面
            response.sendRedirect( "/bigproject/login.do");
            return false;
        }else{//已登录
            return true;
        }
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mv)
            throws Exception {
        // TODO Auto-generated method stub

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

    public void returnErrorResponse(HttpServletResponse response, UserJSONResult result) throws IOException, UnsupportedEncodingException {
        OutputStream out=null;
        try{
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(out!=null){
                out.close();
            }
        }
    }
}
