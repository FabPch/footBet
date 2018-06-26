package fr.arthb.motherrussia.controller;
import fr.arthb.motherrussia.constants.AppConstants;
import fr.arthb.motherrussia.exception.CustomException;
import fr.arthb.motherrussia.service.AuthenticationService;
import fr.arthb.motherrussia.service.GainCalculatorService;
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

	@GetMapping(path = "{gameId}", consumes = "application/json", produces = "application/json;charset=UTF-8")
	public void getGainCalculator(HttpServletRequest request, @PathVariable Integer gameId) {

        int gamblerId = authenticationService.getGamblerId(request);
        // If gambler's id found
        if(
                gamblerId == 10 || // Arth
                gamblerId == 11 || // Fab
                gamblerId == 13 || // Antho
                gamblerId == 20    // Krimo
        ) {
            gainCalculatorService.process(gameId);
        } else {
            throw new CustomException(AppConstants.TRICHE, HttpStatus.FORBIDDEN);
        }

	}
}
