package com.bbookk.config;

import com.bbookk.config.auth.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;

    private static final String [] staticResources = {
            "/css/**",
            "/js/**",
            "/img/**",
            "/favicon.ico",
            "/vendor/**",
            "/static/**",
            "/error"
    };

    @Override
    public void configure(WebSecurity web) throws Exception //정적 자원 인증제외
    {
        web.ignoring().antMatchers(staticResources);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                //admin 권한설정
//                .antMatchers("/member/**").access("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                //user경로는 허용 (회원가입, 로그인 등)
                .antMatchers("/user/**").permitAll()
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginPage("/user/login") // 이 경로의 로그인 요청을 필터링
                .loginProcessingUrl("/login")
                .usernameParameter("loginId")
                .defaultSuccessUrl("/")
                //.successForwardUrl("/member/main")
                //.failureForwardUrl("/user/loginForm")
                .and()

                //Oauth2
                .oauth2Login()
                .loginPage("/user/login")
                .userInfoEndpoint()
                .userService(oAuth2UserService);
                //.invalidateHttpSession(true)
    }

    @Bean
    public BCryptPasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }
}
