package com.bbookk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String [] staticResources = {
            "/css/**",
            "/js/**",
            "/img/**",
            "/favicon.ico",
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
                //member, admin 권한필요
                .antMatchers("/member/**").access("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                //이외에는 허용
                .anyRequest().permitAll()
                .and()

                .formLogin()
                .loginPage("/loginForm") // 이 경로의 로그인 요청을 필터링
                .loginProcessingUrl("/login")
                .usernameParameter("loginId")
                .defaultSuccessUrl("/")
                //.successForwardUrl("/member/main")
                //.failureForwardUrl("/user/loginForm")
                .permitAll()
                .and()

                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/user/loginPage");

                //.invalidateHttpSession(true)
    }

    @Bean
    public BCryptPasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }
}
