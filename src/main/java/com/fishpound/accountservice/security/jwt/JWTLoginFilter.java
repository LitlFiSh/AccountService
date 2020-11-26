package com.fishpound.accountservice.security.jwt;

import com.alibaba.fastjson.JSON;
import com.fishpound.accountservice.entity.Menu;
import com.fishpound.accountservice.entity.Role;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultMenu;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.RoleService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 登录请求过滤器，当接收到登录请求时进入这里
 * 验证用户名、密码，生成 token 并返回结果
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    private RoleService roleService;

    /**
     * 用 setter 注入 Service
     * @param roleService
     */
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public JWTLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, RoleService roleService) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setRoleService(roleService);
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String username = "";
        String password = "";
        username = httpServletRequest.getParameter("username");
        password = httpServletRequest.getParameter("password");
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
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        List<Menu> menus = new ArrayList<>();
        Role role;
        for(GrantedAuthority authority : authorities){
            String roleName = authority.getAuthority().replace("ROLE_", "");
            role = roleService.findByRoleName(roleName);
            switch(role.getId()){
                case 1:
                    Role r1 = roleService.findById(1);
                    menus.addAll(r1.getMenus());
                case 2:
                    Role r2 = roleService.findById(2);
                    menus.addAll(r2.getMenus());
                case 3:
                    Role r3 = roleService.findById(3);
                    menus.addAll(r3.getMenus());
                case 4:
                    Role r4 = roleService.findById(4);
                    menus.addAll(r4.getMenus());
                    break;
                default:
                    break;
            }
//            menus.addAll(role.getMenus());
        }
        List<ResultMenu> resultMenus = new ArrayList<>();
        for(Menu menu : menus){
            resultMenus.add(new ResultMenu(menu.getName(), menu.getPath(), menu.getChildren()));
        }
        printWriter.write(JSON.toJSONString(ResultTool.success(resultMenus)));
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
        if(failed instanceof DisabledException){
            printWriter.write(JSON.toJSONString(ResultTool.fail(ResultCode.USER_ACCOUNT_DISABLE)));
//            System.out.println("LoginFilter: disable");
        } else if(failed instanceof BadCredentialsException){
            printWriter.write(JSON.toJSONString(ResultTool.fail(ResultCode.USER_CREDENTIALS_ERROR)));
//            System.out.println("LoginFilter: password error");
        } else{
            printWriter.write(JSON.toJSONString(ResultTool.fail(ResultCode.FAIL)));
        }
        printWriter.flush();
        printWriter.close();
    }
}
