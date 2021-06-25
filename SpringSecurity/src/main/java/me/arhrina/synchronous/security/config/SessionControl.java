package me.arhrina.synchronous.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
public class SessionControl extends WebSecurityConfigurerAdapter {

    // 동시 세션 제어
    // SessionManagementFilter를 통하여 세션 관리
    // ConcurrentSessionFilter 매 요청마다 세션 만료 여부 체크. session.isExpired() == true => 로그아웃 처리하고 곧바로 오류 페이지 응답. 동시 세션 처리에 사용
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement() // 세션 관리 기능 작동
                .invalidSessionUrl("/invalid") // 세션이 유효하지 않을 때 이동할 페이지. maximumSession 앞에 있어야 동작

                .sessionFixation().changeSessionId() // 세션 고정 보호 동작. 옵션 기본값 servlet 3.1 이상 changeSessionId, 3.1 이하 migrateSession.
                // changeSession, none 공격자 세션 공유됨, migrateSession 공격자 세션 공유됨, newSession. maximumSession 앞에 있어야 동작

                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // 세션 정책. maximumSession 앞에 있어야 동작
                // Always, If_required, Never, Stateless(JWT 등을 사용시 필요)

                .maximumSessions(1) // 최대 허용 가능 세션 수. -1은 무제한 로그인 세션 허용
                .maxSessionsPreventsLogin(true) // 동시 로그인 차단. 세션 1개로 이후 로그인 차단. default false 기존 세션 만료. 세션 2개로 기존 세션을 해제
                .expiredUrl("/expired") // 세션 만료시 이동할 페이지
        ;
    }
}
