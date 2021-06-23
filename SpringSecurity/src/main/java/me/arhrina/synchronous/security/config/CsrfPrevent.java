package me.arhrina.synchronous.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class CsrfPrevent extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 모든 요청에 대해 만들어진 토큰을 http param으로 요구
        // 토큰 값이 서버 저장 값과 불일치하면 요청 처리 안함

        http
                .csrf() // 별도로 지정하지 않아도 default가 ON
                .disable() // 비활성화
        ;
    }
}
