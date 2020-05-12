package com.hqj.bigproject.exception;

import com.hqj.bigproject.utils.UserJSONResult;

import javax.servlet.http.HttpServletRequest;


//@RestControllerAdvice//控制器助手
public class AjaxExceptionHandler {

    //@ExceptionHandler(value = Exception.class)
    public UserJSONResult defaultErrorHandler(HttpServletRequest req,
                                              Exception e) throws Exception {

        e.printStackTrace();
        return UserJSONResult.errorException(e.getMessage());
    }

}
