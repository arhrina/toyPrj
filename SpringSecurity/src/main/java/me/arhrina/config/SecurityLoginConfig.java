package me.arhrina.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityLoginConfig extends WebSecurityConfigurerAdapter {

    // security 기본 인증, 인가 설정 api method override
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .anyRequest().authenticated(); // 그 어떠한 요청에도 인증이 필요한 인가 정책 설정

        http
                .formLogin() // form login 인증 사용
                .loginPage("/login") // 로그인 페이지 url 지정
                .defaultSuccessUrl("/") // 로그인에 성공시 연결될 url 지정
                .failureUrl("/login") // 로그인에 실패시 연결될 url 지정
                .usernameParameter("id") // login form에서 사용할 username parameter명 지정
                .passwordParameter("password") // login form에서 사용할 user password parameter명 지정
                .loginProcessingUrl("/login_proc") // 로그인 form action url 지정.

// UsernamePasswordAuthenticationFilter를 통해 부모클래스의 doFilter를 한다. attemptAuthentication 메소드로 form login의 loginProcessingUrl을
// AntPathRequestMatcher에서 url과 매칭해서 Authentication 인증객체에 저장하여 AuthenticationManager로 넘긴다
// AuthenticationManager는 내부에 AuthenticationProvider에 인증을 위임하여 인증을 맡긴다. exception이 발생하면 filter로 throw
// AuthenticationManager는 AuthenticationProvider에게 받은 인증을 filter로 Authentication 객체를 넘기고, filter는 이를 SecurityContext에 저장하며 SuccessHandler로 던진다

                .successHandler(
                        new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                                System.out.println("authentication: " + authentication.getName());
                                httpServletResponse.sendRedirect("/");
                            }
                        }
                ) // 로그인 성공하고 동작할 커스텀 핸들러. Authentication Success Handler 구현체
                .failureHandler(
                        new AuthenticationFailureHandler() {
                            @Override
                            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                                System.out.println("exception: " + e.getMessage());
                                httpServletResponse.sendRedirect("/login");
                            }
                        }
                ) // 로그인 실패시 동작할 커스텀 핸들러. Authentication Failure Handler 구현체
                .permitAll() // 인증여부와 상관없이 login page는 접근 가능하도록
        ;


        http
                .logout() // 로그아웃 처리 api 사용
                .logoutUrl("/logout") // 로그아웃 처리 URL. default는 POST
// LogoutFilter는 AntPathRequestMatcher가 /logout을 매치
// match하면 Authentication를 SecurityContext 객체에서 꺼내와서 SecurityContextLogoutHandler 클래스에서 세션 무효화, 쿠키 삭제, SecurityContext.clearContext() 실행
// SimpleUrlLogoutSuccessHandler를 호출하여 redirect 시켜줌

                .logoutSuccessUrl("/login") // 로그아웃 성공시 이동할 url
                .deleteCookies("remember-me") // 로그아웃시 삭제할 쿠키명. remember-me는 서버에서 인증시 발급하는 쿠키
                .addLogoutHandler(
                        new LogoutHandler() {
                            @Override
                            public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
                                HttpSession session = httpServletRequest.getSession();
                                session.invalidate();
                            }
                        }
                ) // 로그아웃 때, 세션 무효화 등의 처리를 해야한다. 커스텀 핸들러
                .logoutSuccessHandler(
                        new LogoutSuccessHandler() {
                            @Override
                            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                                httpServletResponse.sendRedirect("/login");
                            }
                        }
                ) // 로그아웃 성공시 후속작업 커스텀 핸들러
        .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenValiditySeconds(3600)
                .alwaysRemember(false)
                .userDetailsService(userDetailsService())
                ;
    }
}
