package com.yalafoot.bet.controller;

import com.yalafoot.bet.constants.AppConstants;
import com.yalafoot.bet.dto.AuthDTO;
import com.yalafoot.bet.dto.UserSessionDTO;
import com.yalafoot.bet.exception.CustomException;
import com.yalafoot.bet.service.AuthenticationService;
import com.yalafoot.bet.utils.AppUtils;

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

	@PostMapping(produces = "application/json;charset=UTF-8")
	public String authenticate(HttpServletRequest request, HttpServletResponse response, @RequestBody AuthDTO authDTO) {

		String token = authenticationService.authenticate(request, authDTO.getLogin(), authDTO.getPassword());
		Cookie cookie = new Cookie(AppConstants.STALINGRAD, token);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);

		return "";
	}

	@DeleteMapping("")
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
			throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
		// Else error
		} else {
			throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
		}
	}
}
