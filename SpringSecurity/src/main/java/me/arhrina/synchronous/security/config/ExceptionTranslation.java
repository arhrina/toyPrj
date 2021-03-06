package me.arhrina.synchronous.security.config;

import me.arhrina.synchronous.security.handler.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@Configuration
public class ExceptionTranslation extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .formLogin()
                .successHandler(
//                        new AuthenticationSuccessHandler() {
//                            @Override
//                            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//                                RequestCache cache = new HttpSessionRequestCache(); // ExceptionTranslationFilter에서 이미 세션 정보를 넣어둠
//                                SavedRequest request = cache.getRequest(httpServletRequest, httpServletResponse);
//                                String redirectUrl = request.getRedirectUrl();
//                                httpServletResponse.sendRedirect(redirectUrl);
//                            }
//                        } // 인증이 성공했을 시
                        customAuthenticationSuccessHandler
                )
                .failureHandler( // 인증이 실패했을 시
                        customAuthenticationFailureHandler
                )
                ;

        http
                .exceptionHandling() // 예외처리 기능 동작하도록
                .authenticationEntryPoint(
                        new AuthenticationEntryPoint() {
                            @Override
                            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                                System.out.println(e.getMessage());
                                httpServletResponse.sendRedirect("/login"); // anyRequest에 대해 제한중일 때는 접근 권한을 antMatchers로 permitAll 해줘야 접근 가능
                            }
                        }
                ) // 인증실패시 처리
                .accessDeniedHandler(
//                        new AccessDeniedHandler() {
//                            @Override
//                            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
//                                System.out.println(e.getMessage());
//                                httpServletResponse.sendRedirect("/denied");
//                            }
//                        }
                        customAccessDeniedHandler()
                ) // 인가실패시 처리
        ;
    }

    private AccessDeniedHandler customAccessDeniedHandler() {
        CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
        accessDeniedHandler.setErrorPage("/denied");
        return accessDeniedHandler;
    }
}
