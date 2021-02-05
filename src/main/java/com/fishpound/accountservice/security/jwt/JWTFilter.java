package com.fishpound.accountservice.security.jwt;

import com.alibaba.fastjson.JSON;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.CacheService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * 前置拦截，当请求除登录 url 外的所有地址都会进入这里
 * 验证 token 的合法性
 */
public class JWTFilter extends BasicAuthenticationFilter {
    private final static Logger logger = LoggerFactory.getLogger(JWTFilter.class);
    private CacheService cacheService;

    private void setCacheService(CacheService cacheService){
        this.cacheService = cacheService;
    }

    public JWTFilter(AuthenticationManager authenticationManager,
                     CacheService cacheService)
    {
        super(authenticationManager);
        setCacheService(cacheService);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        if(request.getMethod().equals("OPTIONS")){
//            response.setStatus(HttpServletResponse.SC_OK);
//            return ;
//        }
//        logger.info("filter start.");
        PrintWriter printWriter;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String token = request.getHeader(JWTTokenUtils.TOKEN_HEADER);
        if("".equals(token) || token == null || !token.startsWith(JWTTokenUtils.TOKEN_PREFIX)){
            printWriter = response.getWriter();
            JsonResult result = ResultTool.fail(ResultCode.TOKEN_IS_NULL);
            printWriter.write(JSON.toJSONString(result));
            printWriter.flush();
            printWriter.close();
            return ;
        }
        token = token.replace(JWTTokenUtils.TOKEN_PREFIX, "");
        Claims claims;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(JWTTokenUtils.TOKEN)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            //缓存校验，检验缓存与传过来的token是否一致
            Element element = cacheService.getCacheElement("token", claims.getSubject());
            if(element != null){
                if(!element.getObjectValue().equals(token)){
                    //token不一致
                    printWriter = response.getWriter();
                    JsonResult result = ResultTool.fail(ResultCode.TOKEN_NOT_VALID);
                    printWriter.write(JSON.toJSONString(result));
                    printWriter.flush();
                    printWriter.close();
                    return ;
                }
            } else{
                //缓存中找不到对应token
                printWriter = response.getWriter();
                JsonResult result = ResultTool.fail(ResultCode.TOKEN_NOT_VALID);
                printWriter.write(JSON.toJSONString(result));
                printWriter.flush();
                printWriter.close();
                return ;
            }
            //token过期时间验证，如果token过期时间小于 10分钟，则签发新token，在缓存中覆盖原有token
            Date now = new Date();
            if((claims.getExpiration().getTime() - now.getTime()) < 10*60*1000){
                String authoritiesStr = (String) claims.get("authorities");
                List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesStr);
                String tokenNew = JWTTokenUtils.createToken(claims.getSubject(), authorities);
                response.setHeader("Access-Control-Expose-Headers", JWTTokenUtils.TOKEN_HEADER);
                response.setHeader(JWTTokenUtils.TOKEN_HEADER, JWTTokenUtils.TOKEN_PREFIX + tokenNew);
                cacheService.setCacheValue("token", claims.getSubject(), tokenNew);
            }
        } catch(JwtException e){
            printWriter = response.getWriter();
            JsonResult result = ResultTool.fail(ResultCode.TOKEN_NOT_VALID);
            printWriter.write(JSON.toJSONString(result));
            printWriter.flush();
            printWriter.close();
            return ;
        }
        String uid = claims.getSubject();
        request.setAttribute("user", uid);
        String authoritiesStr = (String) claims.get("authorities");
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesStr);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(uid, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        super.doFilterInternal(request, response, chain);
    }
}
