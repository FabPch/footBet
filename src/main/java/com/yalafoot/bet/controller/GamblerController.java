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
        return "yala mon gambler !";
    }

    @GetMapping("{id}")
    public Gambler getGamblerById(@PathVariable Integer id){
        return gamblerService.getOne(id);
    }

    @PostMapping("/add")
    public void addGambler(@RequestBody Gambler gambler){
        gamblerService.save(gambler);
    }


}
