package com.yalafoot.bet.controller;

import com.yalafoot.bet.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/game")
@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon game !";
    }
}
