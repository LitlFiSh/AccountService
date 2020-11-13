//package com.fishpound.accountservice.security.handler;
//
//import com.alibaba.fastjson.JSON;
//import com.fishpound.accountservice.result.JsonResult;
//import com.fishpound.accountservice.result.ResultTool;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//@Component
//public class LoginSuccessHandler implements AuthenticationSuccessHandler {
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        //返回json数据
//        JsonResult result = ResultTool.success();
//        //处理编码方式，防止中文乱码的情况
//        httpServletResponse.setContentType("application/json;charset=utf-8");
//        //塞到HttpServletResponse中返回给前台
//        httpServletResponse.getWriter().write(JSON.toJSONString(result));
//    }
//}
