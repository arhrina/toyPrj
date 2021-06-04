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
}
