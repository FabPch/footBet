package com.yalafoot.bet.service.impl;

import com.yalafoot.bet.constants.AppConstants;
import com.yalafoot.bet.dto.UserSessionDTO;
import com.yalafoot.bet.exception.CustomException;
import com.yalafoot.bet.model.Gambler;
import com.yalafoot.bet.repository.GamblerRepository;
import com.yalafoot.bet.security.JwtTokenProvider;
import com.yalafoot.bet.service.AuthenticationService;
import com.yalafoot.bet.utils.AppUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private GamblerRepository gamblerRepository;

    @Autowired
    private JwtTokenProvider JwtTokenProvider;

    @Override
    public boolean isAuthenticate(String token) {
        return false;
    }

    @Override
    public String authenticate(HttpServletRequest request, String login, String pass) {
        Gambler gambler = gamblerRepository.findByLogin(login);
        String passHash = AppUtils.getPassHashed(pass);
        if (passHash.equalsIgnoreCase(gambler.getPassword())){
            //return AppUtils.getUuid();
			request.getSession().setAttribute(AppConstants.USER_AUTHENT_SESSION, new UserSessionDTO(gambler.getId(), gambler.getLogin(), gambler.getName()));
			request.getSession().setAttribute(AppConstants.GAMBLER_SESSION, gambler);
            return JwtTokenProvider.createToken(login, gambler.getId());
        } else {
            throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
        }
    }


    public int getGamblerId(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserSessionDTO user = (UserSessionDTO) request.getSession().getAttribute(AppConstants.USER_AUTHENT_SESSION);
        return  user.getId();
    }

    //public void getSession()
}
