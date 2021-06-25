package me.arhrina.asynchronous.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.arhrina.asynchronous.domain.AccountDTO;
import me.arhrina.asynchronous.security.token.AjaxAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    // 설정 클래스에 filter 추가

    private ObjectMapper objectMapper = new ObjectMapper();

    public AjaxLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/api/login")); // 해당 url matching시 filter 동작하도록
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(!isAjax(request)) {
            throw new IllegalStateException("Authentication is not supported");
        }

        AccountDTO accountDTO = objectMapper.readValue(request.getReader(), AccountDTO.class);

        if(StringUtils.isEmpty(accountDTO.getId()) || StringUtils.isEmpty(accountDTO.getPassword())) {
            throw new IllegalArgumentException("Username or Password is EMPTY");
        }

        AjaxAuthenticationToken authenticationToken = new AjaxAuthenticationToken(accountDTO.getId(), accountDTO.getPassword());

        return getAuthenticationManager().authenticate(authenticationToken); // 인증처리를 하는 AuthenticationManager에 전달
    }



    private boolean isAjax(HttpServletRequest request) { // http header 정보로 통신 방식 확인
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return true;
        }
        else {
            return false;
        }
    }
}
