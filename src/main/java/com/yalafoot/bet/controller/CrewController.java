package com.yalafoot.bet.controller;

import com.yalafoot.bet.dto.AddItemsDTO;
import com.yalafoot.bet.model.Crew;
import com.yalafoot.bet.model.Gambler;
import com.yalafoot.bet.service.AuthenticationService;
import com.yalafoot.bet.service.CrewService;
import com.yalafoot.bet.service.GamblerService;
import com.yalafoot.bet.service.GameService;
import com.yalafoot.bet.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;


@RequestMapping("/crew")
@RestController
public class CrewController {

    @Autowired
    private CrewService crewService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private GamblerService gamblerService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon crew !";
    }

    @GetMapping("{id}")
    public Crew getCrewById(@PathVariable Integer id){
        return crewService.getOne(id);
    }

    @PostMapping()
    public void addCrew(HttpServletRequest request, @RequestBody Crew crew){
        String uuid = AppUtils.getUuid();
        crew.setUid(uuid);
        int gamblerId = authenticationService.getGamblerId(request);
        Gambler gambler = gamblerService.getOne(gamblerId);
        Set<Gambler> gamblerSet = new HashSet<Gambler>();
        gamblerSet.add(gambler);
        crew.setGamblers(gamblerSet);
        crewService.save(crew);
    }

    @PutMapping("{id}")
    public void updateCrew(@RequestBody AddItemsDTO addItemsDTO, @PathVariable int id){
        crewService.addGamblers(id, addItemsDTO.getIds());
    }
}
