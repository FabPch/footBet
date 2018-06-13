package com.yalafoot.bet.utils;

import com.yalafoot.bet.constants.AppConstants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class AppUtils {

    public static String getCookie(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        String cookieValue = null;
        for (Cookie c : cookies){
            if (c.getName().equalsIgnoreCase(name) && c.getValue() != null){
                cookieValue = c.getValue();
                break;
            }
        }
        return cookieValue;
    }

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
