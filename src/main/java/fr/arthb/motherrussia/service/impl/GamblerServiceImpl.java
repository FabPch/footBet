package fr.arthb.motherrussia.service.impl;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import fr.arthb.motherrussia.exception.CustomException;
import fr.arthb.motherrussia.model.Gambler;
import fr.arthb.motherrussia.model.Game;
import fr.arthb.motherrussia.model.Pronostic;
import fr.arthb.motherrussia.repository.GamblerRepository;
import fr.arthb.motherrussia.service.GamblerService;
import fr.arthb.motherrussia.utils.AppUtils;
import fr.arthb.motherrussia.utils.MailUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GamblerServiceImpl implements GamblerService {

    @Autowired
    private GamblerRepository gamblerRepository;

    private static Logger logger = LoggerFactory.getLogger(GamblerService.class);
    private static String LOG_PRFIX_SAVE = "save() -> ";
    private static String LOG_PREFIX_GETACCURACY = "getAccuracy() -> ";

    @Override
    public Gambler getOne(int id) {
        return gamblerRepository.getOne(id);
    }

    @Override
    public Iterable<Gambler> findAll() {
        return gamblerRepository.findAll();
    }

    public Iterable<Gambler> findAllOrderByGain() {
        return gamblerRepository.findAllOrderByGain();
    }

    public Iterable<Gambler> findAllSecuredOrderByGain() {
        return gamblerRepository.findAllSecuredOrderByGain();
    }

    @Override
    public void save(Gambler gambler) {
        String pwd = gambler.getPassword();
        String passwordStringHashed = AppUtils.getPassHashed(pwd);

        String login = gambler.getLogin();
        String name = gambler.getName();

        logger.info(
            LOG_PRFIX_SAVE +
            "login: " + login +
            "; name: " + name +
            "; password: " + passwordStringHashed
        );
        if (passwordStringHashed != null && !passwordStringHashed.isEmpty()){

            gambler.setPassword(passwordStringHashed);
            // gamblerRepository.save(gambler);

            logger.info(
                LOG_PRFIX_SAVE +
                "Gambler saved: " + gambler.toString()
            );

            JSONObject to = new JSONObject()
                .put("Email", login)
                .put("Name", name);

            JSONObject messageVars = new JSONObject()
                .put("nickname", name);

            try {
                logger.info(
                    LOG_PRFIX_SAVE +
                    "Send signup mail to: <" + name + ">" + login
                );
                MailUtils.sendWithTemplate(
                    "signup",
                    to,
                    "Bienvenue sur MotherRussia, le site de pronostiques entre amis !",
                    messageVars
                );
            } catch (MailjetException e) {
                e.printStackTrace();
            } catch (MailjetSocketTimeoutException e) {
                e.printStackTrace();
            }

        } else {
            logger.error(
                LOG_PRFIX_SAVE +
                "Error while hashing password: " + passwordStringHashed
            );
            throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void delete(int id) {
        gamblerRepository.deleteById(id);
    }

    public JSONObject getAccuracy(int gamblerId) {
        JSONObject accuracyInfos = new JSONObject();
        logger.info(String.format("%sGet current gambler with gamblerId: %s", LOG_PREFIX_GETACCURACY, gamblerId));
        Gambler currentGambler = this.getOne(gamblerId);
        if(currentGambler != null) {
            logger.info(String.format("%sFound gambler; gambler: %s", LOG_PREFIX_GETACCURACY, currentGambler));
            JSONArray fullSuccess = new JSONArray();
            JSONArray success = new JSONArray();
            JSONArray wrong = new JSONArray();
            int fullSuccessCount = 0;
            int successCount = 0;
            int wrongCount = 0;
            Set<Pronostic> pronostics = currentGambler.getPronostics();
            for(Pronostic pronostic: pronostics) {
                Game pGame = pronostic.getGame();
                if(pGame != null) {
                    if(pGame.getProcessed() == 1) {
                        if(pronostic.getGain() == 4) {
                            fullSuccess.put(pronostic);
                            fullSuccessCount++;
                        } else if(pronostic.getGain() == 2) {
                            success.put(pronostic);
                            successCount++;
                        } else if(pronostic.getGain() == 0) {
                            wrong.put(pronostic);
                            wrongCount++;
                        }
                    }
                } else {
                    logger.error(String.format("%sNo game found for pronostic: %s", LOG_PREFIX_GETACCURACY, pronostic.getId()));
                }
            }

            accuracyInfos.put(
                "fullSuccess",
                new JSONObject()
                    .put("count", fullSuccessCount)
                    // .put("pronostics", fullSuccess)
            ).put(
                "success",
                new JSONObject()
                    .put("count", successCount)
                    // .put("pronostics", success)
            ).put(
                "wrong",
                new JSONObject()
                    .put("count", wrongCount)
                    // .put("pronostics", wrong)
            );
        } else {
            logger.error(String.format("%sNo gambler", LOG_PREFIX_GETACCURACY));
        }

        logger.info(String.format("%sReturn %s", LOG_PREFIX_GETACCURACY, accuracyInfos.toString()));
        return accuracyInfos;
    }
}
