package me.arhrina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NormalController {

    @ResponseBody
    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @ResponseBody
    @GetMapping(value = "/shop/mypage")
    public String user() {
        return "access user";
    }

    @ResponseBody
    @GetMapping(value = "/shop/admin/pay")
    public String admin() {
        return "access admin";
    }

    @ResponseBody
    @GetMapping(value = "/shop/admin/**")
    public String sys() {
        return "access sys";
    }
}
