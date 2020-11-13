//package com.fishpound.accountservice.security.handler;
//
//import com.alibaba.fastjson.JSON;
//import com.fishpound.accountservice.result.JsonResult;
//import com.fishpound.accountservice.result.ResultCode;
//import com.fishpound.accountservice.result.ResultTool;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 登录失败处理逻辑
// * 在其他过滤器中已经完成了结果的返回
// */
//@Component
//public class LoginFailureHandler implements AuthenticationFailureHandler {
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        JsonResult result = null;
//        if(e instanceof DisabledException){
//            //账户不可用
//            result = ResultTool.fail(ResultCode.USER_ACCOUNT_DISABLE);
//        } else if(e instanceof BadCredentialsException){
//            //密码错误
//            result = ResultTool.fail(ResultCode.USER_CREDENTIALS_ERROR);
//        } else if(e instanceof InternalAuthenticationServiceException){
//            //账户不存在
//            result = ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
//        } else{
//            //其他错误
//            result = ResultTool.fail(ResultCode.FAIL);
//        }
//        //处理编码方式，防止中文乱码的情况
//        httpServletResponse.setContentType("application/json;charset=utf-8");
//        //塞到HttpServletResponse中返回给前台
//        httpServletResponse.getWriter().write(JSON.toJSONString(result));
//    }
//}
