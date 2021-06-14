package com.fishpound.accountservice.security.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fishpound.accountservice.entity.Menu;
import com.fishpound.accountservice.entity.Role;
import com.fishpound.accountservice.entity.Settings;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultMenu;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.CacheService;
import com.fishpound.accountservice.service.MenuService;
import com.fishpound.accountservice.service.RoleService;
import com.fishpound.accountservice.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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
    private final static Logger logger = LoggerFactory.getLogger(JWTLoginFilter.class);
    private RoleService roleService;
    private CacheService cacheService;
    private SettingsService settingsService;
    private MenuService menuService;

    /**
     * 用 setter 注入 Service
     */
    private void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    private void setCacheService(CacheService cacheService){
        this.cacheService = cacheService;
    }
    private void setSettingsService(SettingsService settingsService){
        this.settingsService = settingsService;
    }
    private void setMenuService(MenuService menuService){
        this.menuService = menuService;
    }

    public JWTLoginFilter(String defaultFilterProcessesUrl,
                          AuthenticationManager authenticationManager,
                          RoleService roleService,
                          CacheService cacheService,
                          SettingsService settingsService,
                          MenuService menuService)
    {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setRoleService(roleService);
        setAuthenticationManager(authenticationManager);
        setCacheService(cacheService);
        setSettingsService(settingsService);
        setMenuService(menuService);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
//        logger.info("login filter start");
        String username = "";
        String password = "";
//        username = httpServletRequest.getParameter("username");
//        password = httpServletRequest.getParameter("password");
        if(httpServletRequest.getMethod().equals("OPTIONS")){
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return null;
        }
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
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(JSON.toJSONString(ResultTool.fail("获取用户名密码失败")));
            writer.flush();
            writer.close();
            return null;
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
//        logger.info("login success.");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        List<Menu> menus = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        Role role;
        JWTUser jwtUser = (JWTUser) authResult.getPrincipal();
        String token = JWTTokenUtils.createToken(jwtUser.getId(), jwtUser.getAuthorities());
        response.setHeader("Access-Control-Expose-Headers", JWTTokenUtils.TOKEN_HEADER);
        response.setHeader(JWTTokenUtils.TOKEN_HEADER, JWTTokenUtils.TOKEN_PREFIX + token);
        PrintWriter printWriter = response.getWriter();
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        for(GrantedAuthority authority : authorities){
            String roleName = authority.getAuthority().replace("ROLE_", "");
            role = roleService.findByRoleName(roleName);
            resultMap.put("role", role.getId());
            switch(role.getId()){
                case 1:
                    //管理员
//                    Role r1 = roleService.findById(1);
//                    menus.addAll(r1.getMenus());
                    menus.addAll(roleService.findById(4).getMenus());
                    menus.addAll(roleService.findById(1).getMenus());
                    break;
                case 2:
                    //主管院领导
//                    Role r2 = roleService.findById(2);
//                    menus.addAll(r2.getMenus());
                    menus.addAll(roleService.findById(4).getMenus());
                    menus.addAll(roleService.findById(2).getMenus());
                    break;
                case 3:
                    //部门领导
//                    Role r3 = roleService.findById(3);
//                    menus.addAll(r3.getMenus());
                    menus.addAll(roleService.findById(4).getMenus());
                    menus.addAll(roleService.findById(3).getMenus());
                    break;
                case 4:
                    //普通用户
//                    Role r4 = roleService.findById(4);
//                    menus.addAll(r4.getMenus());
                    menus.addAll(roleService.findById(4).getMenus());
                    break;
                default:
                    break;
            }
//            menus.addAll(role.getMenus());
            Settings settings = settingsService.findByDescription(jwtUser.getId());
            if(settings != null) {
                if (settings.getValue().equals("2")) {
                    menus.add(menuService.findByName("申请单管理"));
                    menus.add(menuService.findByName("设备列表"));
                    menus.add(menuService.findByName("采购单"));
                } else if(role.getId() == 1){
                    menus.add(menuService.findByName("采购单"));
                }
            } else if(role.getId() == 1){
                menus.add(menuService.findByName("申请单管理"));
                menus.add(menuService.findByName("设备列表"));
                menus.add(menuService.findByName("采购单"));
            }
        }
        List<ResultMenu> resultMenus = new ArrayList<>();
        for(Menu menu : menus){
            resultMenus.add(new ResultMenu(menu.getName(), menu.getPath(), menu.getChildren()));
        }
        resultMap.put("menu", resultMenus);   //菜单
        resultMap.put("loginName", jwtUser.getUsername());   //用户名
        resultMap.put("deptName", jwtUser.getDeptName());   //用户所在部门名称
        printWriter.write(JSON.toJSONString(ResultTool.success(resultMap)));
        printWriter.flush();
        printWriter.close();

        //缓存处理
        cacheService.setCacheValue("token",jwtUser.getId(), token);
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
//            logger.info("login failed. reason: {}.", ResultCode.USER_ACCOUNT_DISABLE.getMessage());
        } else if(failed instanceof BadCredentialsException){
            printWriter.write(JSON.toJSONString(ResultTool.fail(ResultCode.USER_CREDENTIALS_ERROR)));
//            logger.info("login failed. reason: {}.", ResultCode.USER_CREDENTIALS_ERROR.getMessage());
        } else{
            printWriter.write(JSON.toJSONString(ResultTool.fail(ResultCode.FAIL)));
//            logger.info("login failed. reason: unknown reason.");
        }
        printWriter.flush();
        printWriter.close();
    }
}
