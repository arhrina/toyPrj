package me.arhrina.asynchronous.security.provider;

import me.arhrina.asynchronous.domain.AccountDTO;
import me.arhrina.asynchronous.security.token.AjaxAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class AjaxAuthenticationProvider implements AuthenticationProvider {

    // 인증 검증 자체는 Form 방식과 다르지 않다
    // 인증 객체만 별도로 만든 Token으로 변경

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = authentication.getName();
        String password = (String) authentication.getCredentials();


        AccountDTO accountDTO = new AccountDTO();
        // filter에서 account 관련하여 권한을 얻어오는 business logic 구현

        return new AjaxAuthenticationToken(accountDTO.getId(), null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AjaxAuthenticationToken.class);
    }
}
