package fr.arthb.motherrussia.controller;

import fr.arthb.motherrussia.constants.AppConstants;
import fr.arthb.motherrussia.dto.PronoDTO;
import fr.arthb.motherrussia.dto.UserSessionDTO;
import fr.arthb.motherrussia.exception.CustomException;
import fr.arthb.motherrussia.model.Gambler;
import fr.arthb.motherrussia.model.Game;
import fr.arthb.motherrussia.model.Pronostic;
import fr.arthb.motherrussia.service.AuthenticationService;
import fr.arthb.motherrussia.service.GamblerService;
import fr.arthb.motherrussia.service.GameService;
import fr.arthb.motherrussia.service.PronosticService;
import fr.arthb.motherrussia.utils.AppUtils;
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
        if(user != null) {
            Set<Pronostic> pronostics = gamblerService.getOne(user.getId()).getPronostics();
            Map<String, PronoDTO> pronos = AppUtils.getPronosticsByGameId(pronostics);
            return pronos;
        } else {
            throw new CustomException(AppConstants.TRICHE, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public void addPronostic(HttpServletRequest request, @RequestBody Pronostic pronostic){
        int gamblerId = authenticationService.getGamblerId(request);
        // If gambler's id found
        if(gamblerId != -1) {
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
    }

    @PutMapping
    public void updatePronostic(HttpServletRequest request, @RequestBody Pronostic pronostic){
        int gamblerId = authenticationService.getGamblerId(request);

        // If gambler's id found
        if(gamblerId != -1) {
            Game game = gameService.getOne(pronostic.getGame().getId());
            boolean checkTime = pronosticService.checkTime(game);
            if (checkTime){
                pronosticService.update(pronostic.getProno1(), pronostic.getProno2(), pronostic.getGame().getId(), gamblerId);
            } else {
                throw new CustomException(AppConstants.TRICHE, HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new CustomException(AppConstants.TRICHE, HttpStatus.FORBIDDEN);
        }
    }
}
