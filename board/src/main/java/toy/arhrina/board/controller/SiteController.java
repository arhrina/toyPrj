package toy.arhrina.board.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toy.arhrina.board.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SiteController {

    @Autowired
    MemberService memberService;

    @GetMapping(value = "/sign-in")
    public String signIn() {
        return "/common/signIn";
    }

    @PostMapping(value = "/sign-in")
    public ResponseEntity signIn(@RequestBody String s) {
        if(memberService.checkMemberLogin(s)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/join")
    public String join() {
        return "/common/join";
    }

    @ResponseBody
    @PostMapping(value = "/joinMember")
    public String join(@RequestBody String s) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> jsonMap = mapper.readValue(s, HashMap.class);
        if(memberService.checkDuplicationMember(jsonMap)) {
            if(memberService.saveMember(jsonMap)) {
                return "success";
            }
            else
                return "false save";
        }
        else {
            return "error";
        }
    }

    @GetMapping(value = "/logout") // 로그아웃 GET 방식 처리
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login";
    }
}
