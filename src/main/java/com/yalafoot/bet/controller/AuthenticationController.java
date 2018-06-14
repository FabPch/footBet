package com.yalafoot.bet.controller;

import com.google.gson.Gson;
import com.yalafoot.bet.constants.AppConstants;
import com.yalafoot.bet.dto.AuthDTO;
import com.yalafoot.bet.dto.UserSessionDTO;
import com.yalafoot.bet.service.AuthenticationService;
import com.yalafoot.bet.utils.AppUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

	@Autowired
	AuthenticationService authenticationService;

	@PostMapping()
	public void authenticate(HttpServletRequest request, HttpServletResponse response, @RequestBody AuthDTO authDTO) {
		String loginCookie = AppUtils.getCookie(request, AppConstants.STALINGRAD);
		
		if(loginCookie == null) {
			String token = authenticationService.authenticate(request, authDTO.getLogin(), authDTO.getPassword());
			Cookie cookie = new Cookie(AppConstants.STALINGRAD, token);
			cookie.setHttpOnly(true);
			response.addCookie(cookie);
		} else {
			getSession(request, response);
		}
	}

	@GetMapping()
	public void getSession(HttpServletRequest request, HttpServletResponse response) {
		String loginCookie = AppUtils.getCookie(request, AppConstants.STALINGRAD);
		
		if(loginCookie == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);			
		} else {
			UserSessionDTO user = (UserSessionDTO) request.getSession().getAttribute(AppConstants.USER_AUTHENT_SESSION);
			response.setStatus(HttpServletResponse.SC_OK);
			try {
				Gson json = new Gson();
				response.getWriter().write(json.toJson(user));
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
