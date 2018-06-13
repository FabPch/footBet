package com.yalafoot.bet.service;

public interface AuthenticationService {

    public boolean isAuthenticate(String token);

    public String authenticate(String login, String pass);
}
