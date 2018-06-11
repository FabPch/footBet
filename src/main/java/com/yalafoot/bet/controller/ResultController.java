package com.yalafoot.bet.controller;

import com.yalafoot.bet.model.Result;
import com.yalafoot.bet.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/result")
@RestController
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon result !";
    }

    @CrossOrigin
    @GetMapping("{id}")
    public Result getResultById(@PathVariable Integer id){
        return resultService.getOne(id);
    }

    @PostMapping("/add")
    public void addResult(@RequestBody Result result){
        resultService.save(result);
    }
}
