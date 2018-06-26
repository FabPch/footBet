package fr.arthb.motherrussia.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.arthb.motherrussia.model.Gambler;
import fr.arthb.motherrussia.model.Pronostic;
import fr.arthb.motherrussia.repository.GamblerRepository;
import fr.arthb.motherrussia.repository.PronosticRepository;
import fr.arthb.motherrussia.service.GainCalculatorService;
import fr.arthb.motherrussia.service.GamblerService;
import fr.arthb.motherrussia.service.GameService;
import fr.arthb.motherrussia.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GainCalculatorServiceImpl implements GainCalculatorService {

    @Autowired
    private GamblerRepository gamblerRepository;

    @Autowired
    private GamblerService gamblerService;

    @Autowired
    private GameService gameService;

    @Autowired
    private PronosticRepository pronosticRepository;

    private final String USER_AGENT = "Mozilla/5.0";

    public void process(int gameId) {

        String url = "http://api.football-data.org/v1/fixtures/" + String.valueOf(gameId);

        URL obj = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        StringBuffer response = new StringBuffer();
        try {
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //Request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonObject responseJson = new JsonParser().parse(response.toString()).getAsJsonObject();
        JsonObject fixtureResults = responseJson.getAsJsonObject("fixture").getAsJsonObject("result");

        int fixtureResultsHome = fixtureResults.get("goalsHomeTeam").getAsInt();
        int fixtureResultsAway = fixtureResults.get("goalsAwayTeam").getAsInt();

        Set<Pronostic> pronostics = gameService.getOne(gameId).getPronostics();
        for (Pronostic pronostic : pronostics) {
            int gain = 0;

            // OK winning team
            if(AppUtils.getWiningTeam(pronostic.getProno1(), pronostic.getProno2()) == AppUtils.getWiningTeam(fixtureResultsHome, fixtureResultsAway)) {
                gain ++;

                // OK 1 team
                if(
                        (pronostic.getProno1() == fixtureResultsHome && pronostic.getProno2() != fixtureResultsAway) ||
                                (pronostic.getProno1() != fixtureResultsHome && pronostic.getProno2() == fixtureResultsAway)
                ) {
                    gain ++;
                }
            }

            // OK all pronostics
            if(pronostic.getProno1() == fixtureResultsHome && pronostic.getProno2() == fixtureResultsAway) {
                gain = 4;
            }

            pronostic.setGain(gain);
            pronosticRepository.save(pronostic);
        }


        Iterable<Gambler> gamblers = gamblerService.findAll();
        for (Gambler gambler : gamblers) {
            int gain = gamblerRepository.getGainSum(gambler.getId());
            gambler.setGain(gain);
            gamblerRepository.save(gambler);
        }

        // param userId - int gainSum = gamblerRepository.getGainSum();
    }

}
