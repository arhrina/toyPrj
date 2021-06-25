package me.arhrina.asynchronous.security.config;

import me.arhrina.asynchronous.handler.AjaxFailureHandler;
import me.arhrina.asynchronous.handler.AjaxSuccessHandler;
import me.arhrina.asynchronous.security.common.AjaxAccessDeniedHandler;
import me.arhrina.asynchronous.security.common.AjaxLoginAuthenticationEntryPoint;
import me.arhrina.asynchronous.security.filter.AjaxLoginProcessingFilter;
import me.arhrina.asynchronous.security.provider.AjaxAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Order(0)
public class AjaxSecurityConfig extends WebSecurityConfigurerAdapter { // 설정클래스가 여러개 있을 경우, 스프링에 동작 순서를 줘야한다

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AjaxSuccessHandler();
    };

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AjaxFailureHandler();
    };

    // 생성한 Provider를 인증에서 사용하도록 설정하기 위해 override
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ajaxAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider ajaxAuthenticationProvider() {
        return new AjaxAuthenticationProvider();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**, /api**")
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()

                /**
                 * add 관련해서 4개의 api가 존재. Filter의 위치를 지정하여 추가
                 * 1. addFilterBefore
                 *      - 추가하는 필터를 기존 특정 필터 전에 동작
                 * 2. addFilter
                 *      - 가장 마지막에 필터 추가
                 * 3. addFilterAfter
                 *      - 추가하는 필터를 기존 특정 필터 바로 뒤에 동작
                 * 4. addFilterAt
                 *      - 특정 필터를 대체하고자 할 때
                 *
                 */
                .addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(new AjaxLoginAuthenticationEntryPoint()) // 인증 예외 처리
            .accessDeniedHandler(ajaxAccessDeniedHandler()) // 인가 예외 처리
        ;
    }

    @Bean
    public AccessDeniedHandler ajaxAccessDeniedHandler() {
        return new AjaxAccessDeniedHandler();
    }

    @Bean
    public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
        AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();
        ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());

        ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        ajaxLoginProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

        return ajaxLoginProcessingFilter;
    }
}
