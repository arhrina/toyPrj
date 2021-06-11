package me.arhrina.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@ResponseBody
public class SecurityController {

    @GetMapping(value = "/test")
    public String test(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityContext context = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if(auth == context) {
            return "true";
        }
        return "false";
    }

    @GetMapping(value = "/thread")
    public String thread(HttpSession session) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    }
                }
        ).start();
        return "thread";
    }

    @GetMapping(value = "user")
    public String user() {
        return "user";
    }
}
