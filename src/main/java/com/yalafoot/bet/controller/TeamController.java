package com.yalafoot.bet.controller;

import com.yalafoot.bet.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/team")
@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/test")
    public String getTest(){
        return "yala ma team !";
    }
}
