package fr.arthb.motherrussia.utils;

import fr.arthb.motherrussia.dto.PronoDTO;
import fr.arthb.motherrussia.exception.CustomException;
import fr.arthb.motherrussia.model.Pronostic;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.http.HttpStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

    public static void invalidateCookies(HttpServletResponse response, Cookie[] cookies){
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    public static String md5(String str) {
        byte[] uniqueKey = str.getBytes();
        byte[] hash      = null;

        try
        {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Error("No MD5 support in this VM.");
        }

        StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1)
            {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            }
            else
                hashString.append(hex.substring(hex.length() - 2));
        }
        return hashString.toString();
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

    public static Map getPronosticsByGameId(Set<Pronostic> pronostics){
        Map<String, PronoDTO> pronos = new HashMap<>();
        if (pronostics != null){
            for (Pronostic p : pronostics){
                PronoDTO pronoDTO = new PronoDTO(p.getProno1(), p.getProno2(), p.getGain());
                pronos.put(String.valueOf(p.getGame().getId()), pronoDTO);
            }
        }
        return pronos;
    }

    public static int getWiningTeam(int goalsHomeTeam, int goalsAwayTeam) {
        int winningTeam = 0;
        if(goalsHomeTeam > goalsAwayTeam) {
            winningTeam = 1;
        } else if(goalsAwayTeam > goalsHomeTeam) {
            winningTeam = 2;
        }
        return winningTeam;
    }
}
