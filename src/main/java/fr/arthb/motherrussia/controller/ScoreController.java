package fr.arthb.motherrussia.controller;

import fr.arthb.motherrussia.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/score")
@RestController
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon score !";
    }
}
