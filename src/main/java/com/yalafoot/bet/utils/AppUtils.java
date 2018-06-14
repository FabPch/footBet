package com.yalafoot.bet.utils;

import com.yalafoot.bet.constants.AppConstants;
import com.yalafoot.bet.exception.CustomException;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.http.HttpStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class AppUtils {

    public static String getCookie(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        String cookieValue = null;
        if (cookies != null){
            for (Cookie c : cookies){
                if (c.getName().equalsIgnoreCase(name) && c.getValue() != null){
                    cookieValue = c.getValue();
                    break;
                }
            }
        }
        return cookieValue;
    }

    public static String getPassHashed(String pass){
        MessageDigest digest = null;
        String passHashed;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (digest != null){
            byte[] passwordHashed = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            passHashed = new String(Hex.encode(passwordHashed));
        } else {
            throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
        }
        return passHashed;
    }

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
