package com.fishpound.accountservice.security.handler;

import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public JsonResult handlerRuntimeException(RuntimeException e){
        return ResultTool.fail("服务器错误");
    }

    @ExceptionHandler(ClassCastException.class)
    public JsonResult handlerClassCastException(ClassCastException e){
        return ResultTool.fail("服务器错误");
    }

    @ExceptionHandler(IOException.class)
    public JsonResult handlerIOException(IOException e){
        return ResultTool.fail("服务器错误");
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public JsonResult handletHttpException(HttpMessageNotReadableException e){
        return ResultTool.fail("请求参数错误，读取参数失败");
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public JsonResult handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        return ResultTool.fail("请求失败，不支持该请求方法");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public JsonResult handlerNotFoundException(NoHandlerFoundException e){
//        System.out.println("NotFound: " + e.getMessage());
        return ResultTool.fail(ResultCode.FAIL);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public JsonResult handlerUsernameNotFoundException(UsernameNotFoundException e){
//        System.out.println("Global Not Found" + e.getMessage());
        return ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public JsonResult handlerMissingServletRequestParameterException(MissingServletRequestParameterException e){
//        System.out.println("ExceptionHandler: " + e.getMessage());
        return ResultTool.fail(ResultCode.PARAM_IS_NULL);
    }

    @ExceptionHandler(NullPointerException.class)
    public JsonResult handlerNullPointerException(NullPointerException e){
//        System.out.println("ExceptionHandler: " + e.getMessage());
        return ResultTool.fail(ResultCode.PARAM_IS_NULL);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public JsonResult handlerObjectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException e){
//        System.out.println("ExceptionHandler: " + e.getMessage());
        return ResultTool.fail("申请单已被更新");
    }

    @ExceptionHandler(Exception.class)
    public JsonResult handlerException(Exception e){
        return ResultTool.fail("未知错误");
    }
}
