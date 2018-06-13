package com.yalafoot.bet.controller;

import com.yalafoot.bet.constants.AppConstants;
import com.yalafoot.bet.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/login")
    public HttpServletResponse authenticate(HttpServletResponse response, String login, String pass){
        String token = authenticationService.authenticate(login, pass);
        Cookie cookie = new Cookie(AppConstants.STALINGRAD, token);
        response.addCookie(cookie);
        return response;
    }
}
