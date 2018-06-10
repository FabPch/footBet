package com.yalafoot.bet.controller;

import com.yalafoot.bet.model.Crew;
import com.yalafoot.bet.service.CrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/crew")
@RestController
public class CrewController {

    @Autowired
    private CrewService crewService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon crew !";
    }

    @CrossOrigin
    @GetMapping("{id}")
    public Crew getCrewById(@PathVariable Integer id){
        return crewService.getOne(id);
    }

    @PostMapping("/add")
    public void addCrew(@RequestBody Crew crew){
        crewService.save(crew);
    }

    @PutMapping("/update")
    public void updateCrew(@RequestBody Integer crewId, Set<Integer> gamblerIds){

    }
}
