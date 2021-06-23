package me.arhrina.synchronous.security.config;

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
public class SecurityLogoutConfig extends WebSecurityConfigurerAdapter {

    // security 기본 인증, 인가 설정 api method override
    @Override
    protected void configure(HttpSecurity http) throws Exception {
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
        ;
    }
}
