package com.yalafoot.bet.controller;

import com.yalafoot.bet.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/result")
@RestController
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon result !";
    }
}
