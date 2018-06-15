package com.yalafoot.bet.controller;

import com.yalafoot.bet.constants.AppConstants;
import com.yalafoot.bet.dto.PronoDTO;
import com.yalafoot.bet.dto.TeamDTO;
import com.yalafoot.bet.dto.UserSessionDTO;
import com.yalafoot.bet.exception.CustomException;
import com.yalafoot.bet.model.Gambler;
import com.yalafoot.bet.model.Game;
import com.yalafoot.bet.model.Pronostic;
import com.yalafoot.bet.service.AuthenticationService;
import com.yalafoot.bet.service.GamblerService;
import com.yalafoot.bet.service.GameService;
import com.yalafoot.bet.service.PronosticService;
import com.yalafoot.bet.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

@RequestMapping("/pronostic")
@RestController
public class PronosticController {

    @Autowired
    private PronosticService pronosticService;

    @Autowired
    private GamblerService gamblerService;

    @Autowired
    private GameService gameService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/test")
    public String getTest(){
        return "yala mon prono !";
    }

    @CrossOrigin
    @GetMapping(produces = "application/json;charset=UTF-8")
    public Map getPronosticById(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserSessionDTO user = (UserSessionDTO) request.getSession().getAttribute(AppConstants.USER_AUTHENT_SESSION);
        Set<Pronostic> pronostics = gamblerService.getOne(user.getId()).getPronostics();
        Map<String, PronoDTO> pronos = AppUtils.getPronosticsByGameId(pronostics);
        return pronos;
    }

    @PostMapping
    public void addPronostic(HttpServletRequest request, @RequestBody Pronostic pronostic){
        int gamblerId = authenticationService.getGamblerId(request);
        Gambler gambler = gamblerService.getOne(gamblerId);
        Game game = gameService.getOne(pronostic.getGame().getId());
        boolean checkUnicity = pronosticService.checkUnicity(pronostic, gambler);
        boolean checkTime = pronosticService.checkTime(game);
        if (checkUnicity && checkTime){
            pronostic.setGambler(gambler);
            pronosticService.save(pronostic);
        } else {
            throw new CustomException(AppConstants.TRICHE, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping
    public void updatePronostic(HttpServletRequest request, @RequestBody Pronostic pronostic){
        int gamblerId = authenticationService.getGamblerId(request);
        Game game = gameService.getOne(pronostic.getGame().getId());
        boolean checkTime = pronosticService.checkTime(game);
        if (checkTime){
            pronosticService.update(pronostic.getProno1(), pronostic.getProno2(), pronostic.getGame().getId(), gamblerId);
        } else {
            throw new CustomException(AppConstants.TRICHE, HttpStatus.UNAUTHORIZED);
        }
    }
}
