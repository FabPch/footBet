package fr.arthb.motherrussia.service.impl;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import fr.arthb.motherrussia.controller.GamblerController;
import fr.arthb.motherrussia.exception.CustomException;
import fr.arthb.motherrussia.model.Gambler;
import fr.arthb.motherrussia.repository.GamblerRepository;
import fr.arthb.motherrussia.service.GamblerService;
import fr.arthb.motherrussia.utils.AppUtils;
import fr.arthb.motherrussia.utils.MailUtils;

import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GamblerServiceImpl implements GamblerService {

    @Autowired
    private GamblerRepository gamblerRepository;

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

    private static Logger logger = LoggerFactory.getLogger(GamblerService.class);
    private static String LOG_PRFIX_SAVE = "save() -> ";

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
}
