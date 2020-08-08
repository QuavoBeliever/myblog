package com.lzy.config;

import com.lzy.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserServiceImpl userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //请求验证
        http.authorizeRequests()
                .antMatchers("/","/index","/register","/login","/toLogin").permitAll()
                .antMatchers("/*").authenticated();

        //登录
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/toLogin")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index");

        //注销
        http.headers().contentTypeOptions().disable();
        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.logout().logoutSuccessUrl("/");

        //记住我
        http.rememberMe().rememberMeParameter("remember");
    }

    // 用户授权验证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    // 密码加密方式
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
