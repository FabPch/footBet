package fr.arthb.motherrussia.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface AuthenticationService {

    public boolean isAuthenticate(String token);

    public String authenticate(HttpServletRequest request, String login, String pass);

    public int getGamblerId(HttpServletRequest request);

    public void changePass(String login, String pass, String passNew);
}
