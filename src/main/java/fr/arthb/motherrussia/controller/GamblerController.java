package fr.arthb.motherrussia.controller;

import fr.arthb.motherrussia.constants.AppConstants;
import fr.arthb.motherrussia.exception.CustomException;
import fr.arthb.motherrussia.model.Gambler;
import fr.arthb.motherrussia.service.AuthenticationService;
import fr.arthb.motherrussia.service.GamblerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/gambler")
@RestController
public class GamblerController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private GamblerService gamblerService;

    private static Logger logger = LoggerFactory.getLogger(GamblerController.class);

    @GetMapping("/test")
    public String getTest(){
        return "yala mon gambler !";
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public Iterable<Gambler> getGamblers(HttpServletRequest request){
        int gamblerId = authenticationService.getGamblerId(request);
        // If gambler's id found
        if(gamblerId != -1) {
            return gamblerService.findAllSecuredOrderByGain();
        } else {
            throw new CustomException(AppConstants.TRICHE, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("{id}")
    public Gambler getGamblerById(@PathVariable Integer id){
        return gamblerService.getOne(id);
    }

    @PostMapping()
    public void addGambler(@RequestBody Gambler gambler){
        logger.info("addGambler() -> gambler: " + gambler.toString());
        gamblerService.save(gambler);
    }


}
