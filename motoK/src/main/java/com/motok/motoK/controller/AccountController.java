package com.motok.motoK.controller;

import com.motok.motoK.domain.vo.DailyAccountVO;
import com.motok.motoK.service.impl.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {


    private final AccountService accountService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private String viewList(Model model) {
        List<DailyAccountVO> list = accountService.findList();
        model.addAttribute("list", list);
        return "/account/list";
    }

    @RequestMapping(value = "/dailySettlement", method = RequestMethod.GET)
    public String daily(Model model) {
        String s = LocalDateTime.now().toString().substring(0, 10);
        model.addAttribute("modelDay", s);
        return "/account/dailySettlement";
    }

    @RequestMapping(value = "/settlementWrite", method = RequestMethod.POST)
    @ResponseBody
    public String dailyWrite(@RequestBody(required = false) DailyAccountVO vo) {
        String s = accountService.일일입력(vo);
        if("error".equals(s)) {
            return "error";
        }
        return "success";
    }
}
