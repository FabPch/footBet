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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger logger = LoggerFactory.getLogger(GainCalculatorService.class);

    private final String USER_AGENT = "Mozilla/5.0";

    private final String LOG_PREFIX = "process() -> ";

    public void process(int gameId) {

        String url = "http://api.football-data.org/v1/fixtures/" + String.valueOf(gameId);
        logger.info(LOG_PREFIX + "Call endpoint: " + url);
        URL obj = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        boolean responseOK = true;
        StringBuffer response = new StringBuffer();
        try {
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //Request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            if(con.getResponseCode() != 200) {
                logger.error(LOG_PREFIX + "ResponseCode: "+ con.getResponseCode());
                responseOK = false;
            } else {
                logger.info(LOG_PREFIX + "ResponseCode: "+ con.getResponseCode());
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;


                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
        } catch (IOException e) {
            responseOK = false;
            e.printStackTrace();
        }

        if(responseOK == false) {
            logger.error(LOG_PREFIX + "Aborted process");
        } else {
            logger.info(LOG_PREFIX + "Response: " + response.toString());
            JsonObject responseJson = new JsonParser().parse(response.toString()).getAsJsonObject();
            JsonObject fixtureResults = responseJson.getAsJsonObject("fixture").getAsJsonObject("result");

            int fixtureResultsHome = fixtureResults.get("goalsHomeTeam").getAsInt();
            int fixtureResultsAway = fixtureResults.get("goalsAwayTeam").getAsInt();

            logger.info(LOG_PREFIX + "gameService.getOne(" + String.valueOf(gameId) + ").getPronostics()");
            Set<Pronostic> pronostics = gameService.getOne(gameId).getPronostics();

            if(pronostics == null) {
                logger.error(LOG_PREFIX + "No pronostics for game: " + String.valueOf(gameId) + " found");
            } else {
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

                    logger.info(
                        LOG_PREFIX +
                        "Pronostic: " + String.valueOf(pronostic.getId()) +
                        "; gamblerId: " + String.valueOf(pronostic.getGambler().getId()) +
                        "; pronos: " +
                        String.valueOf(pronostic.getProno1()) + "-" + String.valueOf(pronostic.getProno2()) +
                        "; scores: " +
                        String.valueOf(fixtureResultsHome) + "-" + String.valueOf(fixtureResultsAway) +
                        "; gain: " + String.valueOf(gain)
                    );
                    pronostic.setGain(gain);
                    pronosticRepository.save(pronostic);
                }
                logger.info(LOG_PREFIX + "Ended saving pronostics");
            }

            logger.info(LOG_PREFIX + "Find all gamblers");
            Iterable<Gambler> gamblers = gamblerService.findAll();

            logger.info(LOG_PREFIX + "Found "+ Long.toString(gamblers.spliterator().getExactSizeIfKnown()) + " gamblers");
            for (Gambler gambler : gamblers) {
                int gain = gamblerRepository.getGainSum(gambler.getId());
                logger.info(
                    LOG_PREFIX +
                    "Gambler: " + String.valueOf(gambler.getId()) +
                    "; previousGain: " + String.valueOf(gambler.getGain()) +
                    "; newGain: " + String.valueOf(gain)
                );
                gambler.setGain(gain);
                gamblerRepository.save(gambler);
            }
            logger.info(LOG_PREFIX + "Ended saving gamblers");
        }






        // param userId - int gainSum = gamblerRepository.getGainSum();
    }

}
