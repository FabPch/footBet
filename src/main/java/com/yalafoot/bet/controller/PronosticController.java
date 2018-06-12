package com.yalafoot.bet.controller;

import com.yalafoot.bet.service.PronosticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/pronostic")
@RestController
public class PronosticController {

    @Autowired
    private PronosticService pronosticService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon prono !";
    }
}
