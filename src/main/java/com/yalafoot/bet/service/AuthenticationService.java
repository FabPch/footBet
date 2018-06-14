package com.yalafoot.bet.service;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    public boolean isAuthenticate(String token);

    public String authenticate(HttpServletRequest request, String login, String pass);
}
