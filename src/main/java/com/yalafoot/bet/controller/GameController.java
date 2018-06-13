package com.yalafoot.bet.controller;

import com.yalafoot.bet.model.Game;
import com.yalafoot.bet.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/game")
@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon game !";
    }

    @CrossOrigin
    @GetMapping("{id}")
    public Game getGameById(@PathVariable Integer id){
                return gameService.getOne(id);
           }

    @PostMapping()
    public void addGame(@RequestBody Game game){
                gameService.save(game);
            }
}
