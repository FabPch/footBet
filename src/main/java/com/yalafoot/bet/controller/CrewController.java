package com.yalafoot.bet.controller;

import com.yalafoot.bet.dto.AddItemsDTO;
import com.yalafoot.bet.model.Crew;
import com.yalafoot.bet.service.CrewService;
import com.yalafoot.bet.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/crew")
@RestController
public class CrewController {

    @Autowired
    private CrewService crewService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon crew !";
    }

    @GetMapping("{id}")
    public Crew getCrewById(@PathVariable Integer id){
        return crewService.getOne(id);
    }

    @PostMapping()
    public void addCrew(@RequestBody Crew crew){
        String uuid = AppUtils.getUuid();
        crew.setUid(uuid);
        crewService.save(crew);
    }

    @PutMapping("{id}")
    public void updateCrew(@RequestBody AddItemsDTO addItemsDTO){
        crewService.addGamblers(addItemsDTO.getId(), addItemsDTO.getIds());
    }
}
