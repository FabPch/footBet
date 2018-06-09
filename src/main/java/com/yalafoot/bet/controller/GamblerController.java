package com.yalafoot.bet.controller;

import com.yalafoot.bet.model.Gambler;
import com.yalafoot.bet.service.GamblerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/gambler")
@RestController
public class GamblerController {

    @Autowired
    private GamblerService gamblerService;

    @GetMapping("/test")
    public String getTest(){
        return "yala ma gueule !";
    }

    @GetMapping("/{id}")
    public Gambler getGamblerById(@PathVariable Integer id){
        return gamblerService.getOne(id);
    }

    @PutMapping("/update")
    public void updateGambler(@RequestParam String name, String login, String password, byte[] photo){
        Gambler gambler = new Gambler();
        gambler.setName(name);
        gambler.setLogin(login);
        gambler.setPassword(password);
        gamblerService.save(gambler);
    }
}
