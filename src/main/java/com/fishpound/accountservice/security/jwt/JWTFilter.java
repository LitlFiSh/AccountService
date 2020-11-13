package com.fishpound.accountservice.security.jwt;

import com.alibaba.fastjson.JSON;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
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
import java.util.List;

public class JWTFilter extends BasicAuthenticationFilter {
    public JWTFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "192.168.50.20");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Headers", "*");
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return ;
        }
        PrintWriter printWriter;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String token = request.getHeader(JWTTokenUtils.TOKEN_HEADER);
        if("".equals(token) || token == null || !token.startsWith(JWTTokenUtils.TOKEN_PREFIX)){
            printWriter = response.getWriter();
            JsonResult result = ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE);
            printWriter.write(JSON.toJSONString(result));
            printWriter.flush();
            printWriter.close();
            return ;
        }
        Claims claims;
        try{
            token = token.replace(JWTTokenUtils.TOKEN_PREFIX, "");
            claims = Jwts.parserBuilder()
                    .setSigningKey(JWTTokenUtils.TOKEN)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch(JwtException e){
            printWriter = response.getWriter();
            JsonResult result = ResultTool.fail(ResultCode.PARAM_NOT_VALID);
            printWriter.write(JSON.toJSONString(result));
            printWriter.flush();
            printWriter.close();
            return ;
        }
//        Claims claims = JWTTokenUtils.getTokenBody(token);
//        if(!claims.getExpiration().before(new Date())){
//            JsonResult result = ResultTool.fail(ResultCode.USER_NOT_LOGIN);
//            printWriter.write(JSON.toJSONString(result));
//            printWriter.flush();
//            printWriter.close();
//            return ;
//        }
        String username = claims.getSubject();
        String authoritiesStr = (String) claims.get("authorities");
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesStr);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        super.doFilterInternal(request, response, chain);
    }
}
