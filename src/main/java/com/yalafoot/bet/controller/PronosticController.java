package com.yalafoot.bet.controller;

import com.yalafoot.bet.constants.AppConstants;
import com.yalafoot.bet.dto.PronoDTO;
import com.yalafoot.bet.dto.TeamDTO;
import com.yalafoot.bet.dto.UserSessionDTO;
import com.yalafoot.bet.model.Pronostic;
import com.yalafoot.bet.service.GamblerService;
import com.yalafoot.bet.service.PronosticService;
import com.yalafoot.bet.utils.AppUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

@RequestMapping("/pronostic")
@RestController
public class PronosticController {

    @Autowired
    private PronosticService pronosticService;

    @Autowired
    private GamblerService gamblerService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon prono !";
    }

    @CrossOrigin
    @GetMapping(produces = "application/json;charset=UTF-8")
    public String getPronosticById(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserSessionDTO user = (UserSessionDTO) request.getSession().getAttribute(AppConstants.USER_AUTHENT_SESSION);
        Set<Pronostic> pronostics = gamblerService.getOne(user.getId()).getPronostics();
        JSONObject jsonObject = AppUtils.getPronosticsByGameId(pronostics);
        return jsonObject.toString();
    }

    @PostMapping()
    public void addPronostic(@RequestBody Pronostic pronostic){
        pronosticService.save(pronostic);
    }
}
