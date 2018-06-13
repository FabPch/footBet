package com.yalafoot.bet.controller;

import com.yalafoot.bet.dto.PronoDTO;
import com.yalafoot.bet.dto.TeamDTO;
import com.yalafoot.bet.model.Pronostic;
import com.yalafoot.bet.service.PronosticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/pronostic")
@RestController
public class PronosticController {

    @Autowired
    private PronosticService pronosticService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon prono !";
    }

    @CrossOrigin
    @GetMapping("{id}")
    public PronoDTO getPronosticById(@PathVariable Integer id){
        Pronostic pronostic= pronosticService.getOne(id);
        TeamDTO team1 = new TeamDTO();
        TeamDTO team2 = new TeamDTO();
        team1.setRes(pronostic.getProno1());
        team2.setRes(pronostic.getProno2());
        PronoDTO pronoDTO = new PronoDTO();
        pronoDTO.setId(pronostic.getId());
        pronoDTO.setGameId(pronostic.getGame().getId());
        pronoDTO.setTeam1(team1);
        pronoDTO.setTeam2(team2);
        return pronoDTO;
    }

    @PostMapping()
    public void addPronostic(@RequestBody Pronostic pronostic){
        pronosticService.save(pronostic);
    }
}
