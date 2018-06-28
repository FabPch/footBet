package fr.arthb.motherrussia.controller;

import fr.arthb.motherrussia.constants.AppConstants;
import fr.arthb.motherrussia.dto.AuthDTO;
import fr.arthb.motherrussia.dto.UserSessionDTO;
import fr.arthb.motherrussia.exception.CustomException;
import fr.arthb.motherrussia.service.AuthenticationService;
import fr.arthb.motherrussia.utils.AppUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

	@Autowired
	AuthenticationService authenticationService;

	private static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@PostMapping()
	public void authenticate(HttpServletRequest request, HttpServletResponse response, @RequestBody AuthDTO authDTO) {

		String loggerMessage = String.format("authenticate() -> login: %s; password: %s", authDTO.getLogin(), authDTO.getPassword().replaceAll("(.)", "*"));
		logger.info(loggerMessage);

		String token = authenticationService.authenticate(request, authDTO.getLogin(), authDTO.getPassword());
		Cookie cookie = new Cookie(AppConstants.STALINGRAD, token);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	@DeleteMapping()
	public void disconnect(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(false);
		if (session != null){
			session.invalidate();
			Cookie[] cookies = request.getCookies();
			AppUtils.invalidateCookies(response, cookies);
		}
	}

	@GetMapping(produces = "application/json;charset=UTF-8")
	public UserSessionDTO getSession(HttpServletRequest request, HttpServletResponse response) {

		// Get user from session
		UserSessionDTO user = (UserSessionDTO) request.getSession().getAttribute(AppConstants.USER_AUTHENT_SESSION);

		// Get login cookie
		String loginCookie = AppUtils.getCookie(request, AppConstants.STALINGRAD);

		// If user is in session return it
		if(user != null) {
			return user;

		// Else If there is a login cookie (decrypt and return user)
		} else if(loginCookie != null) {
			throw new CustomException(AppConstants.ACCESS_DENIED, HttpStatus.FORBIDDEN);
		// Else error
		} else {
			throw new CustomException(AppConstants.ACCESS_DENIED, HttpStatus.FORBIDDEN);
		}
	}

	@PutMapping
	public void changePass(HttpServletRequest request, @RequestBody AuthDTO authDTO){
		int gamblerId = authenticationService.getGamblerId(request);
		if (gamblerId != -1){
			authenticationService.changePass(authDTO.getLogin(), authDTO.getPassword(), authDTO.getPasswordNew());
		} else {
			throw new CustomException(AppConstants.ACCESS_DENIED, HttpStatus.FORBIDDEN);
		}
	}
}
