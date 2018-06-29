package fr.arthb.motherrussia.utils;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class RestUtils {

    public static JSONObject get(String url) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
            // Add query parameter
            //.queryParam("pageNumber", "1")
            //.queryParam("walletId", "2323JK")
            //.queryParam("pageSize", "10");

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(builder.toUriString(), String.class);
        JSONObject jsonObj = new JSONObject(response);

        return jsonObj;
    }
}
