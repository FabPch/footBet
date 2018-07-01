package fr.arthb.motherrussia.service.impl;

import fr.arthb.motherrussia.model.Game;
import fr.arthb.motherrussia.repository.GameRepository;
import fr.arthb.motherrussia.service.GameService;
import fr.arthb.motherrussia.utils.RestUtils;
import org.apache.tomcat.jni.Error;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    // Logger init
    private static Logger logger = LoggerFactory.getLogger(GameService.class);
    private static String LOG_PRFIX_GETLIVEGAMES = "getLiveGames() -> ";
    private static String LOG_PRFIX_GETLIVEGAMESFROMFIFA = "getLiveGamesFromFifa() -> ";
    private static String LOG_PRFIX_DELETECACHE = "deleteCache() -> ";

    @Override
    public Game getOne(int id) {
        return gameRepository.getOne(id);
    }

    @Override
    @Cacheable("games")
    public ArrayList<Game> findAll() {
        return (ArrayList<Game>) gameRepository.findAll();
    }

    @Override
    public void save(Game game) {
        gameRepository.save(game);
    }

    @Override
    public void delete(int id) {
        gameRepository.deleteById(id);
    }

    @CacheEvict(value = { "games", "liveGamesFromFifa" }, allEntries=true, beforeInvocation = true)
    public void deleteCache() {
        logger.info(String.format("%s Processing",LOG_PRFIX_DELETECACHE));
    }

    @Cacheable("games")
    public JSONObject getLiveGames() {

        /**
         * Make the bridge between current database based on api.football-data.org
         */
        logger.info(String.format("%s Getting data from DB", LOG_PRFIX_GETLIVEGAMES));
        ArrayList<Game> dbGames = this.findAll();

        logger.info(String.format("%s DB Games: %s", LOG_PRFIX_GETLIVEGAMES, dbGames.spliterator().getExactSizeIfKnown()));

        JSONObject liveGames = new JSONObject();
        logger.info(String.format("%s Init liveGames: %s", LOG_PRFIX_GETLIVEGAMES, liveGames.toString()));

        logger.info(String.format("%s Call FIFA API", LOG_PRFIX_GETLIVEGAMES));
        JSONObject jsonResponse = this.getLiveGamesFromFifa();
        if(jsonResponse != null) {

            JSONArray games = jsonResponse.getJSONArray("Results");

            JSONArray sortedGames = new JSONArray();

            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
            for (int i = 0; i < games.length(); i++) {
                jsonValues.add(games.getJSONObject(i));
            }

            logger.info(String.format("%s Sorting results by date", LOG_PRFIX_GETLIVEGAMES));
            Collections.sort( jsonValues, new Comparator<JSONObject>() {
                //You can change "Name" with "ID" if you want to sort by ID
                private static final String KEY_NAME = "Date";

                @Override
                public int compare(JSONObject a, JSONObject b) {
                    String valA = new String();
                    String valB = new String();

                    Integer intA = 0;
                    Integer intB = 0;

                    try {
                        valA = (String) String.valueOf(a.get(KEY_NAME));
                        valB = (String) String.valueOf(b.get(KEY_NAME));
                        // intA = (Integer) a.get(KEY_NAME);
                        // intB = (Integer) b.get(KEY_NAME);
                    }
                    catch (JSONException e) {
                        //do something
                    }

                    // if(intA > intB) {
                    //     return 1;
                    // } else {
                    //     return -1;
                    // }
                    return valA.compareTo(valB);
                    //if you want to change the sort order, simply use the following:
                    //return -valA.compareTo(valB);
                }
            });

            String jsonModel = "{" +
                "\"_links\":{" +
                    "\"self\":{" +
                        "\"href\":\"http://api.football-data.org/v1/fixtures/%s\"" +
                    "}," +
                    "\"selfFifa\":{" +
                        "\"href\":\"https://api.fifa.com/api/v1/live/football/17/254645/275093/%s?language=en-US\"" +
                    "}" +
//                    "\"competition\":{" +
//                        "\"href\":\"http://api.football-data.org/v1/competitions/467\"" +
//                    "}," +
//                    "\"homeTeam\":{" +
//                        "\"href\":\"http://api.football-data.org/v1/teams/808\"" +
//                    "}," +
//                    "\"awayTeam\":{" +
//                        "\"href\":\"http://api.football-data.org/v1/teams/801\"" +
//                    "}" +
                "}," +
                "\"idApiFoot\":%s," +
                "\"idFifa\":%s," +
                "\"date\":\"%s\"," +
                "\"status\":\"%s\"," +
                "\"homeTeamName\":\"%s\"," +
                "\"awayTeamName\":\"%s\"," +
                "\"result\":{" +
                    "\"goalsHomeTeam\":%s," +
                    "\"goalsAwayTeam\":%s" +
//                    "\"goalsAwayTeam\":%s," +
//                    "\"halfTime\":{" +
//                        "\"goalsHomeTeam\":2," +
//                        "\"goalsAwayTeam\":0" +
//                    "}" +
                "}," +
//                "\"odds\":null," +
                "\"stadium\":\"%s\"," +
                "\"stadiumCity\":\"%s\"," +
                "\"number\":%s,"+
                "\"matchDay\":%s"+
            "}";
            logger.info(String.format("%s Fixture template: %s", LOG_PRFIX_GETLIVEGAMES, jsonModel));

            for (int i = 0; i < games.length(); i++) {
                JSONObject currentGame = (JSONObject) jsonValues.get(i);
                String date = currentGame.getString("Date");

                String idApiFoot = "null";
                String idFifa = "null";

                String stadium = "";
                String stadiumCity = "";

                int officialityStatus = 0;
                String winnerTeam = "null";
                String status = "TIMED";

                String matchDay = "null";
                String number = "null";

                String homeTeamScore = "null";
                String homeTeamName = "";

                String awayTeamScore = "null";
                String awayTeamName = "";

                logger.info(String.format("%s Processing fixture", LOG_PRFIX_GETLIVEGAMES));
                try {
                    idFifa = currentGame.getString("IdMatch");

                    stadium = currentGame.getJSONObject("Stadium").getJSONArray("Name").getJSONObject(0).getString("Description");
                    stadiumCity = currentGame.getJSONObject("Stadium").getJSONArray("CityName").getJSONObject(0).getString("Description");

                    officialityStatus = currentGame.getInt("OfficialityStatus");
                    winnerTeam = String.valueOf(currentGame.get("Winner"));
                    if(officialityStatus == 2 || winnerTeam != "null") {
                        status = "FINISHED";
                    } else if(officialityStatus == 1) {
                        status = "IN_PLAY";
                    } else {
                        status = "TIMED";
                    }

                    number = currentGame.get("MatchNumber").toString();
                    matchDay = currentGame.get("MatchDay").toString();

                    JSONObject homeTeam = currentGame.getJSONObject("Home");
                    JSONObject awayTeam = currentGame.getJSONObject("Away");

                    if(homeTeam != null) {
                        homeTeamScore = homeTeam.get("Score").toString();

                        JSONArray homeTeamNameArr = homeTeam.getJSONArray("TeamName");
                        if(homeTeamNameArr != null) {
                            JSONObject homeTeamNameObj = homeTeamNameArr.getJSONObject(0);
                            if(homeTeamNameObj != null) {
                                homeTeamName = homeTeamNameObj.getString("Description");
                            }
                        }
                    }

                    if(awayTeam != null) {
                        awayTeamScore = awayTeam.get("Score").toString();

                        JSONArray awayTeamNameArr = awayTeam.getJSONArray("TeamName");
                        if(awayTeamNameArr != null) {
                            JSONObject awayTeamNameObj = awayTeamNameArr.getJSONObject(0);
                            if(awayTeamNameObj != null) {
                                awayTeamName = awayTeamNameObj.getString("Description");
                            }
                        }
                    }
                    logger.info(String.format("%s Fixture idFifa: %s; %s-%s", LOG_PRFIX_GETLIVEGAMES, idFifa, homeTeamName, awayTeamName));

                    /**
                     * Find the same match in DB
                     * to bridge FIFA match with the one on api.football-data.org
                     */
                    for(int j=0; j<((ArrayList) dbGames).size(); j++) {

                        try {
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            format.setTimeZone(TimeZone.getTimeZone("GMT"));
                            Date newDate = format.parse(date);
                            Date gettedDate = new Date(dbGames.get(j).getDate().getTime());


                            if (
                                dbGames.get(j).getTeam1().getName().equals(homeTeamName) &&
                                dbGames.get(j).getTeam2().getName().equals(awayTeamName) &&
                                newDate.equals(gettedDate)
                            ) {
                                idApiFoot = String.valueOf(dbGames.get(j).getId());
                                break;
                            }
                        } catch (ParseException e) {

                        }

                    }


                } catch (JSONException e) {
                    logger.error(e.toString());
                }

                String finalStrJson = String.format(
                    jsonModel,

                    idApiFoot,
                    idFifa,
                    idApiFoot,
                    idFifa,
                    date,
                    status,
                    homeTeamName,
                    awayTeamName,
                    homeTeamScore,
                    awayTeamScore,
                    stadium,
                    stadiumCity,
                    number,
                    matchDay
                );
                JSONObject finalJson = new JSONObject(finalStrJson);
                sortedGames.put(finalJson);
            }

            liveGames.put("count", sortedGames.length());
            liveGames.put("fixtures", sortedGames);
        } else {
            logger.error(String.format("%s No response", LOG_PRFIX_GETLIVEGAMES));
        }


        return liveGames;
    }

    @Cacheable("liveGamesFromFifa")
    public JSONObject getLiveGamesFromFifa() {
        Date date1 = new Date();
        String idSeason = "254645";
        String idCompetition = "17";
        String languageResults = "en-US"; // fr-FR is also avalaible
        String countResults = "100";
        String endpoint = String.format("https://api.fifa.com/api/v1/calendar/matches?idseason=%s&idcompetition=%s&language=%s&count=%s", idSeason, idCompetition, languageResults, countResults);
        logger.info(String.format("%s Prepare endpoint: %s", LOG_PRFIX_GETLIVEGAMESFROMFIFA, endpoint));
        JSONObject jsonResponse = RestUtils.get(endpoint);
        Date date2 = new Date();
        logger.info(String.format("%s %s ms; Response: %s", LOG_PRFIX_GETLIVEGAMESFROMFIFA, (long) date2.getTime() - date1.getTime(), jsonResponse.toString()));
        return jsonResponse;
    }
}
