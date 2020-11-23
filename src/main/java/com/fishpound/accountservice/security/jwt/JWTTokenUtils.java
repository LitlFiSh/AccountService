package com.fishpound.accountservice.security.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;

/**
 * JWT 相应参数及方法
 */
public class JWTTokenUtils {
    //头部字段名
    public static final String TOKEN_HEADER = "Authorization";
    //token 前缀
    public static final String TOKEN_PREFIX = "Bearer ";
    //token 生成秘钥
    public static final String TOKEN = "AERTRY+DtghvjhHGUhcg=jkUIYjhbkijoulUKYHbkhuygfg";
    public static final String ROLE_CLAIMS ="ROLE_" ;
    // 过期时间是3600秒，既是1个小时
    private static final long EXPIRATION = 3600L;
    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    /**
     * token 生成函数
     * 注：生成的 token 有效时间为1小时
     * @param username
     * @param authorities
     * @return
     */
    public static String createToken(String username, Collection<? extends GrantedAuthority> authorities){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        StringBuffer roles = new StringBuffer();
        for(GrantedAuthority authority : authorities){
            roles.append(authority.getAuthority()).append(",");
        }
        JwtBuilder builder = Jwts.builder()
                .claim("authorities", roles)
                .setSubject(username)
                .setExpiration(new Date(nowMillis + EXPIRATION * 1000))
                .setIssuedAt(now)
                .signWith(generateKey());
        return builder.compact();
    }

    private static SecretKey generateKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(TOKEN));
    }

//    public static Claims getTokenBody(String token){
//        token = token.replace(TOKEN_PREFIX, "");
//        return Jwts.parserBuilder()
//                .setSigningKey(TOKEN)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }

//    public static String getUsername(String token){
//        return getTokenBody(token).getSubject();
//    }
//
//    public static boolean isExpiration(String token){
//        return getTokenBody(token).getExpiration().before(new Date());
//    }
}
