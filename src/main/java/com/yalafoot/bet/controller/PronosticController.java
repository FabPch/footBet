package com.yalafoot.bet.controller;

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
    public Pronostic getPronosticById(@PathVariable Integer id){
        return pronosticService.getOne(id);
    }

    @PostMapping("/add")
    public void addPronostic(@RequestBody Pronostic pronostic){
        pronosticService.save(pronostic);
    }
}
