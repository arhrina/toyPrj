package me.arhrina.asynchronous.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    // 비동기 통신이므로 인증이 실패했을 시 http body에 정보를 담아 보내주어야 하므로 핸들러를 별도 구현
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errMsg = "Invalid Username or Password";

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if(exception instanceof BadCredentialsException) {
            errMsg = "Invalid Username or Password";
        }
        else if(exception instanceof DisabledException) {
            errMsg = "Locked";
        }
        else if(exception instanceof CredentialsExpiredException) {
            errMsg = "Expired Password";
        }

        objectMapper.writeValue(response.getWriter(), errMsg);
    }
}
