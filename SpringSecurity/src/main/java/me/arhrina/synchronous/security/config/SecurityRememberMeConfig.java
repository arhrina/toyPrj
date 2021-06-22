package me.arhrina.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityRememberMeConfig extends WebSecurityConfigurerAdapter {

    // security 기본 인증, 인가 설정 api method override
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .rememberMe() // RememberMe api 정책 설정
                .rememberMeParameter("remember") // 쿠키 remember-me 기본 파라미터명을 변경
                .tokenValiditySeconds(3600) // default는 14일. 기준 Second
                .alwaysRemember(true) // remember me 기능이 활성화되지 않아도 항상 실행. default는 false
                .userDetailsService(userDetailsService()) // remember-me를 수행할 때 필요한 클래스. 필수기본설정
        ;
    }

    // RememberMeAuthentcationFilter를 통해 SecurityContext의 Authentication 객체의 정보가 null이고 Remember-me cookie를 가지고 있는 경우 처리
    // RememberMeServices interface를 통하여 구현체 2개로 토큰 쿠키를 추출하여 인증을 처리
}
