package com.fishpound.accountservice.security.handler;

import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    public JsonResult handlerNotFoundException(Exception e){
        System.out.println("NotFound: " + e.getMessage());
        return ResultTool.fail(ResultCode.FAIL);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public JsonResult handlerUsernameNotFoundException(Exception e){
        System.out.println("Global Not Found" + e.getMessage());
        return ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
    }
}
