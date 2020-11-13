package com.fishpound.accountservice.security.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JWTLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String username = "";
        String password = "";
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "192.168.50.20");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Max-Age", "86400");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        if(httpServletRequest.getMethod().equals("OPTIONS")){
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return null;
        }
        if(httpServletRequest.getParameter("username").equals("")){
            try {
                BufferedReader streamReader = new BufferedReader( new InputStreamReader(httpServletRequest.getInputStream(), "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();
                String inputStr;
                while ((inputStr = streamReader.readLine()) != null) {
                    responseStrBuilder.append(inputStr);
                }
                JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
                username = jsonObject.getString("username");
                password = jsonObject.getString("password");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            username = httpServletRequest.getParameter("username");
            password = httpServletRequest.getParameter("password");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JWTUser jwtUser = (JWTUser) authResult.getPrincipal();
        String token = JWTTokenUtils.createToken(jwtUser.getUsername(), jwtUser.getAuthorities());
        response.setHeader("Access-Control-Expose-Headers", JWTTokenUtils.TOKEN_HEADER);
        response.setHeader(JWTTokenUtils.TOKEN_HEADER, JWTTokenUtils.TOKEN_PREFIX + token);
        PrintWriter printWriter = response.getWriter();
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        List<String> list = new ArrayList<>();
        for(GrantedAuthority authority : authorities){
            list.add(authority.getAuthority());
        }
        printWriter.write(JSON.toJSONString(ResultTool.success(list)));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSON.toJSONString(ResultTool.fail(ResultCode.USER_CREDENTIALS_ERROR)));
        printWriter.flush();
        printWriter.close();
    }
}
