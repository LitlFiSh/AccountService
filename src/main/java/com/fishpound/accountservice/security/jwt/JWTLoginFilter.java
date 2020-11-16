package com.fishpound.accountservice.security.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fishpound.accountservice.entity.Menu;
import com.fishpound.accountservice.entity.Role;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

/**
 * 登录请求过滤器，当接收到登录请求时进入这里
 * 验证用户名、密码，生成 token 并返回结果
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    @Autowired
    RoleService roleService;

    public JWTLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String username = "";
        String password = "";
//        if(httpServletRequest.getMethod().equals("OPTIONS")){
//            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//            return null;
//        }
//        try {
//            BufferedReader streamReader = new BufferedReader( new InputStreamReader(httpServletRequest.getInputStream(), "UTF-8"));
//            StringBuilder responseStrBuilder = new StringBuilder();
//            String inputStr;
//            while ((inputStr = streamReader.readLine()) != null) {
//                responseStrBuilder.append(inputStr);
//            }
//            JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
//            username = jsonObject.getString("username");
//            password = jsonObject.getString("password");
//            System.out.println(username);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        username = httpServletRequest.getParameter("username");
        password = httpServletRequest.getParameter("password");
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
//        //todo 返回菜单列表
//        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
//        Set<Menu> menuSet = new HashSet<>();
//        for(GrantedAuthority authority : authorities){
//            Role role = roleService.findByRoleName(authority.getAuthority().replace("ROLE_", ""));
//            System.out.println(role.getRoleName());
//            menuSet.addAll(role.getMenuSet());
//        }
        printWriter.write(JSON.toJSONString(ResultTool.success()));
        printWriter.flush();
        printWriter.close();
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
