package com.yalafoot.bet.controller;

import com.yalafoot.bet.constants.AppConstants;
import com.yalafoot.bet.dto.AuthDTO;
import com.yalafoot.bet.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public String authenticate(HttpServletResponse response, @RequestBody AuthDTO authDTO){
        String token = authenticationService.authenticate(authDTO.getLogin(), authDTO.getPassword());
        Cookie cookie = new Cookie(AppConstants.STALINGRAD, token);
        response.addCookie(cookie);
        return "yo";
    }
}
