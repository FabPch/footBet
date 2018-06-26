package fr.arthb.motherrussia.controller;

import fr.arthb.motherrussia.dto.AddItemsDTO;
import fr.arthb.motherrussia.model.Crew;
import fr.arthb.motherrussia.model.Gambler;
import fr.arthb.motherrussia.service.AuthenticationService;
import fr.arthb.motherrussia.service.CrewService;
import fr.arthb.motherrussia.service.GamblerService;
import fr.arthb.motherrussia.utils.AppUtils;
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

        // If gambler's id found
        if(gamblerId != -1) {
            Gambler gambler = gamblerService.getOne(gamblerId);
            Set<Gambler> gamblerSet = new HashSet<Gambler>();
            crew.setGamblers(gamblerSet);
            crewService.save(crew);
            gamblerSet.add(gambler);
        }
    }

    @PutMapping("{id}")
    public void updateCrew(@RequestBody AddItemsDTO addItemsDTO, @PathVariable int id){
        crewService.addGamblers(id, addItemsDTO.getIds());
    }
}
