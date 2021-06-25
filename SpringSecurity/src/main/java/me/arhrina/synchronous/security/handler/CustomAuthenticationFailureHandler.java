package me.arhrina.synchronous.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Invalid Username or Password";

        if(exception instanceof BadCredentialsException) {
            errorMessage = "Invalid Username or Password";
        }
//        else if(exception instanceof CustomProvider의 Exception) {
//
//        }

        setDefaultFailureUrl("/login?error=true&exception=" + exception.getMessage()); // login page에 에러를 get으로 던진다
        // login controller에서 query string을 받아서 처리시키고 antMatchers에 login parameter permitAll 처리하도록

        super.onAuthenticationFailure(request, response, exception);
    }
}
