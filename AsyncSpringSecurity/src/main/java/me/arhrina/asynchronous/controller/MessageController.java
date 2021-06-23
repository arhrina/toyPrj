package me.arhrina.asynchronous.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {

    @ResponseBody
    @GetMapping("/api/messages")
    public String apiMessage() {
        return "messages ok";
    }
}
