package com.example.lottomanual.controller.rest;

import com.example.lottomanual.service.LottoService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {

    @Autowired
    LottoService lottoService;

    @RequestMapping(value = "/getSix", method = RequestMethod.POST, produces = "application/json")
    public String retSixNum(@RequestParam(name = "request") Boolean str) {
        if(str)
            return lottoService.getSix();
        else
            return "{data:error}";
    }
}
