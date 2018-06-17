package com.yalafoot.bet.controller;

import com.yalafoot.bet.constants.AppConstants;
import com.yalafoot.bet.exception.CustomException;
import com.yalafoot.bet.model.Gambler;
import com.yalafoot.bet.service.AuthenticationService;
import com.yalafoot.bet.service.GamblerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/gambler")
@RestController
public class GamblerController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private GamblerService gamblerService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon gambler !";
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public Iterable<Gambler> getGamblers(HttpServletRequest request){
        int gamblerId = authenticationService.getGamblerId(request);
        // If gambler's id found
        if(gamblerId != -1) {
            return gamblerService.findAllOrderByGain();
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
        gamblerService.save(gambler);
    }


}
