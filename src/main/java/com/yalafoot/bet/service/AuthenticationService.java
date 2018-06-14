package com.yalafoot.bet.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface AuthenticationService {

    public boolean isAuthenticate(String token);

    public String authenticate(HttpServletRequest request, String login, String pass);

    public int getGamblerId(HttpServletRequest request);
}
