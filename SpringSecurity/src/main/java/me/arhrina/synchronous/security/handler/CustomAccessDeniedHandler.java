package me.arhrina.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 인증 시도시 발생한 예외는 인증 필터가 받는다
     * <p>
     * 인증 성공 후 자격, 권한이 없는 경우 인가 예외 발생. 인증 필터가 처리하는 것이 아닌 FilterSecurityInterceptor가 인가예외 처리
     * <p>
     * AccessDeniedException을 발생시켜 ExceptionTranslationFilter로 throw하여 accessDeniedHandler 처리
     */

    private String errorPage;

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    // 인가 예외 처리 핸들
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String deniedUrl = errorPage + "?exception=" + accessDeniedException.getMessage();
        // 예외 페이지를 controller에서 생성해서 파라미터 처리

        response.sendRedirect(deniedUrl);
    }
}
