package me.arhrina.asynchronous.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.arhrina.asynchronous.domain.AccountDTO;
import me.arhrina.asynchronous.domain.entity.AccountEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxSuccessHandler implements AuthenticationSuccessHandler {

    ObjectMapper objectMapper = new ObjectMapper();


    // 비동기방식이므로 페이지를 이동시키는 것이 아닌 정보를 http body에 담아서 전송해주어야하므로 handler를 별도 구현
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AccountEntity account = (AccountEntity) authentication.getPrincipal();

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getWriter(), account);
    }
}
