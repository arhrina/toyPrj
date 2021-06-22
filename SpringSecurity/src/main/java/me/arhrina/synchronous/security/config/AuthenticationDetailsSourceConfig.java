package me.arhrina.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.Resource;

@EnableWebSecurity
public class AuthenticationDetailsSourceConfig extends WebSecurityConfigurerAdapter {

    @Autowired
//    @Resource(name="formAuthenticationDetailsSource")
//    @Qualifier("authenticationDetailsSource")
    private AuthenticationDetailsSource formAuthenticationDetailsSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("login_proc")
                .authenticationDetailsSource(formAuthenticationDetailsSource);
        // details 내용에 들어있는 custom 된 값을 가져다 provider에서 추가로 인증 처리 가능
    }
}
