package fr.arthb.motherrussia.controller;

import fr.arthb.motherrussia.model.Game;
import fr.arthb.motherrussia.service.GameService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/game")
@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    // Logger init
    private static Logger logger = LoggerFactory.getLogger(GameController.class);
    private static String LOG_PRFIX_GETLIVEGAMES = "getLiveGames() -> ";
    private static String LOG_PRFIX_DELETECACHE = "deleteCache() -> ";

    @GetMapping(path="/live", produces = "application/json;charset=UTF-8")
    public String getLiveGames(){
        logger.info(String.format("%s Request", LOG_PRFIX_GETLIVEGAMES));
        return gameService.getLiveGames().toString();
    }

    @CrossOrigin
    @GetMapping("{id}")
    public Game getGameById(@PathVariable Integer id){
                return gameService.getOne(id);
           }

    @PostMapping()
    public void addGame(@RequestBody Game game){
                gameService.save(game);
            }

    @DeleteMapping(path="/cache", produces = "application/json;charset=UTF-8")
    public void deleteCache(){
        logger.info(String.format("%s Go to service", LOG_PRFIX_DELETECACHE));
        gameService.deleteCache();
    }
}
