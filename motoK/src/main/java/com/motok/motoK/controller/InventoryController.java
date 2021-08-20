package com.motok.motoK.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/inventory")
public class InventoryController {
    @RequestMapping("/list")
    public String inventoryList() {
        return "/inventory/list";
    }
}
