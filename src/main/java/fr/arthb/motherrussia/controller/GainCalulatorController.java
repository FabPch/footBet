package fr.arthb.motherrussia.controller;
import fr.arthb.motherrussia.constants.AppConstants;
import fr.arthb.motherrussia.exception.CustomException;
import fr.arthb.motherrussia.service.AuthenticationService;
import fr.arthb.motherrussia.service.GainCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/gainCalculator")
@RestController
public class GainCalulatorController {

    @Autowired
    private AuthenticationService authenticationService;

	@Autowired
	private GainCalculatorService gainCalculatorService;

    private static Logger logger = LoggerFactory.getLogger(GainCalulatorController.class);

    private final String LOG_PREFIX = "getGainCalculator() -> ";

	@GetMapping(path = "{gameId}", consumes = "application/json", produces = "application/json;charset=UTF-8")
	public void getGainCalculator(HttpServletRequest request, @PathVariable Integer gameId) {

        logger.info(LOG_PREFIX + "gameId: " + gameId);
        int gamblerId = authenticationService.getGamblerId(request);
        // If gambler's id found
        if(
                gamblerId == 10 || // Arth
                gamblerId == 11 || // Fab
                gamblerId == 13 || // Antho
                gamblerId == 20    // Krimo
        ) {
            logger.info(LOG_PREFIX + "Authorized gambler: " + gamblerId);
            gainCalculatorService.process(gameId);
        } else {
            logger.error(LOG_PREFIX + "Unauthorized gambler: " + gamblerId);
            throw new CustomException(AppConstants.TRICHE, HttpStatus.FORBIDDEN);
        }

	}
}
