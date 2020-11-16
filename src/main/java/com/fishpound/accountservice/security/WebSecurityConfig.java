package com.fishpound.accountservice.security;

//import com.fishpound.accountservice.security.handler.LoginFailureHandler;
//import com.fishpound.accountservice.security.handler.LoginSuccessHandler;
import com.fishpound.accountservice.security.handler.CustomizeAccessDeniedHandler;
import com.fishpound.accountservice.security.handler.CustomizeAuthenticationEntryPoint;
import com.fishpound.accountservice.security.handler.LogoutSuccessHandler;
import com.fishpound.accountservice.security.jwt.JWTFilter;
import com.fishpound.accountservice.security.jwt.JWTLoginFilter;
import com.fishpound.accountservice.service.Impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsServiceImpl userDetailsService;

//    @Autowired   //登陆成功处理逻辑
//            LoginSuccessHandler loginSuccessHandler;
//
//    @Autowired   //登录失败处理逻辑
//            LoginFailureHandler loginFailureHandler;
//
    @Autowired   //登出成功处理逻辑
            LogoutSuccessHandler logoutSuccessHandler;

    @Autowired   //权限不足处理逻辑
            CustomizeAccessDeniedHandler accessDeniedHandler;

    @Autowired   //匿名用户访问无权限资源处理逻辑
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                //只开放登录接口，其他访问路径都需要身份验证
                .antMatchers(HttpMethod.POST, "/user/login").permitAll()
                .antMatchers("/user/**").hasAuthority("ROLE_USER")
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                //登录拦截器，在这里拦下登录请求，判断登陆是否成功，生成token
                .addFilterBefore(new JWTLoginFilter("/user/login",
                        authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                //权限拦截器，拦截每条需要验证的请求，判断携带的token是否存在或有效
//                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new JWTFilter(authenticationManager()))
                //登出处理
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                //登入成功以及失败的处理
                .and()
                .formLogin().permitAll()
//                .successHandler(loginSuccessHandler)
//                .failureHandler(loginFailureHandler)
                //设置不创建session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);
    }
}
