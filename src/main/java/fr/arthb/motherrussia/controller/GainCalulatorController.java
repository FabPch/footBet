package fr.arthb.motherrussia.controller;
import fr.arthb.motherrussia.constants.AppConstants;
import fr.arthb.motherrussia.exception.CustomException;
import fr.arthb.motherrussia.model.Game;
import fr.arthb.motherrussia.service.AuthenticationService;
import fr.arthb.motherrussia.service.GainCalculatorService;
import fr.arthb.motherrussia.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

@RequestMapping("/gainCalculator")
@RestController
public class GainCalulatorController {

    @Autowired
    private AuthenticationService authenticationService;

	@Autowired
	private GainCalculatorService gainCalculatorService;

    @Autowired
    private GameService gameService;

    private static Logger logger = LoggerFactory.getLogger(GainCalulatorController.class);

    private final String LOG_PREFIX_GETGAINCALCULATOR = "getGainCalculator() -> ";
    private final String LOG_PREFIX_REFRESHGAIN = "refreshGain() -> ";


    @GetMapping(path = "{gameId}", consumes = "application/json", produces = "application/json;charset=UTF-8")
	public void getGainCalculator(HttpServletRequest request, @PathVariable Integer gameId) {
        logger.info(String.format("%sManual Game Calculator START", LOG_PREFIX_GETGAINCALCULATOR));
        logger.info(String.format("%sgameId: %s", LOG_PREFIX_GETGAINCALCULATOR, gameId));
        int gamblerId = authenticationService.getGamblerId(request);
        logger.info(String.format("%sManual Game Calculator END", LOG_PREFIX_GETGAINCALCULATOR));
	}

    @Async
    @Scheduled(fixedRate = 120000) // 2min = (2 x 60 (s) x 1000 (ms) )
    public void refreshGain() {
        logger.info(String.format("%sScheduler START refreshing gains", LOG_PREFIX_REFRESHGAIN));
        int gameId = gainCalculatorService.getGameIdByProcessedAndTimePlus90Min();

        if (gameId == 0) {
            logger.warn(String.format("%sNo gameId found for processed games with datetime +90min", LOG_PREFIX_REFRESHGAIN));
        } else {
            logger.info(String.format("%sGameId found for processed games with datetime +90min; gameId: %s", LOG_PREFIX_REFRESHGAIN, gameId));

            Game currentGame = gameService.getOne(gameId);
            if(currentGame.getId() != 0) {
                logger.info(String.format("%sGame found with gameId: %s", LOG_PREFIX_REFRESHGAIN, gameId));

                gainCalculatorService.process(currentGame);
            } else {
                logger.error(String.format("%sNo game found with gameId: %s", LOG_PREFIX_REFRESHGAIN, gameId));
            }
        }
        logger.info(String.format("%sScheduler END refreshing gains", LOG_PREFIX_REFRESHGAIN));
    }
}
