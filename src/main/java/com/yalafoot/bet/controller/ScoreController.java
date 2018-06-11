package com.yalafoot.bet.controller;

import com.yalafoot.bet.model.Score;
import com.yalafoot.bet.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/score")
@RestController
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon score !";
    }

    @CrossOrigin
    @GetMapping("{id}")
    public Score getScoreById(@PathVariable Integer id){
        return scoreService.getOne(id);
    }

    @PostMapping("/add")
    public void addScore(@RequestBody Score score){
        scoreService.save(score);
    }
}
