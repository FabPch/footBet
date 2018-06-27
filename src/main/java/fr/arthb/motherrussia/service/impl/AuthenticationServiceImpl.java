package fr.arthb.motherrussia.service.impl;

import fr.arthb.motherrussia.constants.AppConstants;
import fr.arthb.motherrussia.dto.UserSessionDTO;
import fr.arthb.motherrussia.exception.CustomException;
import fr.arthb.motherrussia.model.Gambler;
import fr.arthb.motherrussia.repository.GamblerRepository;
import fr.arthb.motherrussia.security.JwtTokenProvider;
import fr.arthb.motherrussia.service.AuthenticationService;
import fr.arthb.motherrussia.utils.AppUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private GamblerRepository gamblerRepository;

    @Autowired
    private JwtTokenProvider JwtTokenProvider;

    private static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private static String loggerAuthenticate = "authenticate() -> ";
    @Override
    public boolean isAuthenticate(String token) {
        return false;
    }

    @Override
    public String authenticate(HttpServletRequest request, String login, String pass) {
        logger.info(loggerAuthenticate + "login: " + login + "; password: " + pass.replaceAll(".", "*"));

        Gambler gambler = gamblerRepository.findByLogin(login);

        if(gambler == null) {
            logger.error(loggerAuthenticate + "no gambler found");
            throw new CustomException(AppConstants.NO_USER_FOUND, HttpStatus.FORBIDDEN);
        } else {
            logger.info(loggerAuthenticate + "gambler found");
        }

        String passHash = AppUtils.getPassHashed(pass);
        if (gambler.getPassword() != null && passHash.equalsIgnoreCase(gambler.getPassword())){
            //return AppUtils.getUuid();
			request.getSession().setAttribute(AppConstants.USER_AUTHENT_SESSION, new UserSessionDTO(gambler.getId(), gambler.getLogin(), gambler.getName()));
			request.getSession().setAttribute(AppConstants.GAMBLER_SESSION, gambler);
            logger.info(loggerAuthenticate + "passwords match");
			return JwtTokenProvider.createToken(login, gambler.getId());
        } else {
            logger.error(loggerAuthenticate + "passwords don't match");
            throw new CustomException(AppConstants.ACCESS_DENIED, HttpStatus.FORBIDDEN);
        }
    }


    public int getGamblerId(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserSessionDTO user = (UserSessionDTO) session.getAttribute(AppConstants.USER_AUTHENT_SESSION);
        return (user == null) ? -1 : user.getId();
    }

    @Override
    public void changePass(String login, String pass, String passNew) {
        Gambler gambler = gamblerRepository.findByLogin(login);
        String passHash = AppUtils.getPassHashed(pass);
        if (passNew != null && passHash.equalsIgnoreCase(gambler.getPassword())){
            String passHashNew = AppUtils.getPassHashed(passNew);
            gambler.setPassword(passHashNew);
            gamblerRepository.save(gambler);
        } else {
            throw new CustomException("Access denied or null passNew", HttpStatus.FORBIDDEN);
        }
    }
}
