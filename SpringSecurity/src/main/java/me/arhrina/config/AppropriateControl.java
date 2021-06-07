package me.arhrina.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class AppropriateControl extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("sys").password("{noop}1111").roles("SYS");
        // password의 암호화 알고리즘의 방식(encode)을 prefix로 기재. 안적어줬을시 id->null로 에러 발생. noop은 평문 알고리즘
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/shop/**") // url 방식
                .authorizeRequests() // 인가 api 동작 설정
                    .antMatchers("/shop/login", "shop/users/**").permitAll()
                    .antMatchers("/shop/mypage").hasRole("USER")
                    .antMatchers("/shop/admin/pay").access("hasRole('ADMIN')")
                    .antMatchers("/shop/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
                    .anyRequest().authenticated();
        /**
         * ※ 주의 ※  인가처리는 앞에서부터 확인하므로 설정시 구체적으로 좁은 범위의 경로가 먼저 오고 그보다 큰 범위의 경로가 뒤에 오도록
         * admin/pay는 admin/** 아래 포함되지만 pay가 먼저 있으므로, pay에는 SYS 권한은 접근할 수 없다
         *
         * 표현식
         *
         * authenticated() 인증된 사용자의 접근 허용
         * fullyAuthenticated() 인증된 사용자의 접근 허용. rememeberMe 제외
         * permitAll() 무조건 접근 허용
         * denyAll() 무조건 접근 불허
         * anonymous() 익명사용자만 접근 허용. 다른 역할은 접근 불가
         * rememberMe() rememberMe 인증 사용자 접근 허용
         * access(String) 주어진 SpEL 표현식의 결과가 true면 허용
         * hasRole() 주어진 역할 접근 허용
         * hasAuthority() 주어진 권한 접근 허용
         * hasAnyRole 주어진 권한이 있으면 접근 허용
         * hasAnyAuthority() 주어진 권한 중 어떤 것이라도 있으면 접근 허용
         * hasIpAddress() 주어진 IP 접근 허용
         */
    }
}
