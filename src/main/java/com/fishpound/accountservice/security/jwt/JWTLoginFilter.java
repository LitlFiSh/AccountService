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

/**
 * 登录请求过滤器，当接收到登录请求时进入这里
 * 验证用户名、密码，生成 token 并返回结果
 */
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
        if(httpServletRequest.getParameter("username") == ""){
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

    /**
     * 登陆成功，生成 token 并返回
     * 注意：生成的 token 放置的地方为 response 的头部
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
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

    /**
     * 登录失败，返回失败原因
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
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
