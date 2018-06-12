package com.yalafoot.bet.controller;

import com.yalafoot.bet.model.Gambler;
import com.yalafoot.bet.model.Team;
import com.yalafoot.bet.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/team")
@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/test")
    public String getTest(){
        return "yala ma team !";
    }

    @CrossOrigin
    @GetMapping("{id}")
    public Team getTeamrById(@PathVariable Integer id){
        return teamService.getOne(id);
    }

    @PostMapping("/add")
    public void addTeam(@RequestBody Team team){
        teamService.save(team);
    }
}
