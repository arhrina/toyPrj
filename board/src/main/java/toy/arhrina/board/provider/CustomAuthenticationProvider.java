package toy.arhrina.board.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import toy.arhrina.board.service.MemberContext;

public class CustomAuthenticationProvider implements AuthenticationProvider { // UserDetails를 받아 실제 동작하는 class

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 인증에 관련된 모든 검증 구현

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        MemberContext memberContext = (MemberContext) userDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, memberContext.getMember().getPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }

        // 인증에 성공한 인증객체를 만들어서 Provider를 호출한 Manager에게 return
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberContext.getMember(), null, memberContext.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) { // 현재 parameter의 type과 클래스가 사용하고자 하는 token의 타입이 일치하는지 확인
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
