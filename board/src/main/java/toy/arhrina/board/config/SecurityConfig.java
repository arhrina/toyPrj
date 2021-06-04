package toy.arhrina.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity // WebSecurityConfiguration, SpringWebMvc 등 여러 보안 관련 클래스들을 import하여 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    // api 인증과 인가 설정할 수 있는 method override
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated(); // 어떤 요청에도 인증이 필요한 인가정책 설정

        // form login 인증방식 api
        http
                .formLogin() // 폼 로그인 방식으로 인증
        .loginPage("/sign-in") // 사용자 정의 로그인 페이지 url
        .defaultSuccessUrl("/") // 로그인 성공 후 이동할 페이지 url
        .failureUrl("/sign-in") // 로그인 실패시 이동할 페이지 url
        .usernameParameter("id") // form tag 안의 id parameter명
        .passwordParameter("password") // form tag 안의 password parameter명
        .loginProcessingUrl("/sign-in") // 로그인 Form action url 지정
        .successHandler(
                new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        System.out.println("authentication: " + authentication.getName()); // 인증에 성공한 사용자 이름 출력
                        response.sendRedirect("/");
                    }
                }
        ) // 로그인 성공 후 동작할 커스텀 핸들러. AuthenticationSuccessHandler 구현체
        .failureHandler(
                new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        System.out.println("exception: " + exception.getMessage());
                        response.sendRedirect("/sign-in");
                    }
                }
        ) // 로그인 실패시 동작할 커스텀 핸들러 AuthenticationFailureHandler 구현체
        .permitAll() // 인증여부와 상관없이 login page는 접근 가능
        ;
    }
}
