package me.arhrina.asynchronous.security.config;

import me.arhrina.asynchronous.security.filter.AjaxLoginProcessingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                /**
//                 * add 관련해서 4개의 api가 존재. Filter의 위치를 지정하여 추가
//                 * 1. addFilterBefore
//                 *      - 추가하는 필터를 기존 특정 필터 전에 동작
//                 * 2. addFilter
//                 *      - 가장 마지막에 필터 추가
//                 * 3. addFilterAfter
//                 *      - 추가하는 필터를 기존 특정 필터 바로 뒤에 동작
//                 * 4. addFilterAt
//                 *      - 특정 필터를 대체하고자 할 때
//                 *
//                 */
//                .addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
//        ;
    }
}
