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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(PronosticController.class);
    private static String LOG_PRFIX_UPDATEPRONOSTIC = "updatePronostic() -> ";
    private static String LOG_PRFIX_ADDPRONOSTIC = "addPronostic() -> ";

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
        logger.info(String.format("%sAdd pronostic", LOG_PRFIX_ADDPRONOSTIC));
        int gamblerId = authenticationService.getGamblerId(request);

        // If gambler's id found
        if(gamblerId != -1) {
            logger.info(String.format("%sgamblerId: %s", LOG_PRFIX_ADDPRONOSTIC, gamblerId));

            Gambler gambler = gamblerService.getOne(gamblerId);
            Game game = gameService.getOne(pronostic.getGame().getId());
            boolean checkUnicity = pronosticService.checkUnicity(pronostic, gambler);
            boolean checkTime = pronosticService.checkTime(game);
            if (checkUnicity && checkTime){
                logger.info(String.format(
                    "%sPronostic can be created; pronosticGameId: %s; prono: %s-%s",//; gamblerId: %s",
                    LOG_PRFIX_ADDPRONOSTIC,
                    pronostic.getGame().getId(),
                    pronostic.getProno1(),
                    pronostic.getProno2()
                ));
                pronostic.setGambler(gambler);
                pronosticService.save(pronostic);
                logger.info(String.format("%sPronostic created", LOG_PRFIX_ADDPRONOSTIC));
            } else {
                logger.error(String.format("%sToo late or pronostic already exists, pronostic can't be created; checkTime: %s", LOG_PRFIX_ADDPRONOSTIC, checkTime));
                throw new CustomException(AppConstants.TRICHE, HttpStatus.UNAUTHORIZED);
            }
        } else {
            logger.error(String.format("%sNo gambler found - gamblerId: %s", LOG_PRFIX_ADDPRONOSTIC, gamblerId));
        }
    }

    @PutMapping
    public void updatePronostic(HttpServletRequest request, @RequestBody Pronostic pronostic){
        logger.info(String.format("%sUpdate pronostic", LOG_PRFIX_UPDATEPRONOSTIC));

        int gamblerId = authenticationService.getGamblerId(request);

        // If gambler's id found
        if(gamblerId != -1) {
            logger.info(String.format("%sCurrent gambler: %s", LOG_PRFIX_UPDATEPRONOSTIC, gamblerId));

            logger.info(String.format("%sGet game: gameService.getOne(%s)", LOG_PRFIX_UPDATEPRONOSTIC, pronostic.getGame().getId()));
            Game game = gameService.getOne(pronostic.getGame().getId());
            boolean checkTime = pronosticService.checkTime(game);
            if (checkTime){
                logger.info(String.format("%sOn time, pronostic can be updated; checkTime: %s", LOG_PRFIX_UPDATEPRONOSTIC, checkTime));
                pronosticService.update(pronostic.getProno1(), pronostic.getProno2(), pronostic.getGame().getId(), gamblerId);
            } else {
                logger.error(String.format("%sToo late, pronostic can't be updated; checkTime: %s", LOG_PRFIX_UPDATEPRONOSTIC, checkTime));
                throw new CustomException(AppConstants.TRICHE, HttpStatus.UNAUTHORIZED);
            }
        } else {
            logger.error(String.format("%sNo current gambler found", LOG_PRFIX_UPDATEPRONOSTIC));
            throw new CustomException(AppConstants.TRICHE, HttpStatus.FORBIDDEN);
        }
    }
}
