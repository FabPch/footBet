package fr.arthb.motherrussia.utils;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class MailUtils {

    private static String mjClientVersion = "v3.1";

    private static String propMjApikeyPublic;
    private static String propMjApikeyPrivate;
    private static String propMailFromEmail;
    private static String propMailFromName;

    // Logger init
    private static Logger logger = LoggerFactory.getLogger(MailUtils.class);
    private static String LOG_PRFIX_SENDWITHTMPL = "sendWithTemplate() -> ";

    private static Properties prop;

    static{
        InputStream is = null;
        try {
            prop = new Properties();
            is = ClassLoader.class.getResourceAsStream("/application.properties");
            prop.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPropertyValue(String key){
        return prop.getProperty(key);
    }

    public static void sendWithTemplate(String templateName, JSONObject to, String messageSubject, JSONObject messageVars) throws MailjetException, MailjetSocketTimeoutException {

        String logMessage = String.format(
            "%s templateName: %s; templateId: %s; to: %s; messageSubject: %s; messageVars: %s",
            LOG_PRFIX_SENDWITHTMPL,
            templateName,
            getTemplateId(templateName),
            to.toString(),
            messageSubject,
            messageVars.toString()
        );
        logger.info(logMessage);

        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;

        propMjApikeyPublic = getPropertyValue("security.mailjet.public");
        propMjApikeyPrivate = getPropertyValue("security.mailjet.private");

        propMailFromEmail = getPropertyValue("mail.from.email");
        propMailFromName = getPropertyValue("mail.from.name");

        client = new MailjetClient(propMjApikeyPublic, propMjApikeyPrivate, new ClientOptions(mjClientVersion));

        /**
         *    MESSAGE INPUT JSON
         *    "Messages":[
         *        {
         *              "From": {
         *                  "Email": "arthur@berzieri.fr",
         *                  "Name": "MotherRussia"
         *              },
         *              "To": [
         *                  {
         *                      "Email": "passenger1@example.com",
         *                      "Name": "passenger 1"
         *                  }
         *              ],
         *              "TemplateID": 450727,
         *              "TemplateLanguage": true,
         *              "Subject": "Bienvenue sur Mother Russia, le site de pronostiques",
         *              "Variables": {
         *                  "nickname": ""
         *              }
         *          }
         *    ]
         *
         */
        request = new MailjetRequest(Emailv31.resource)
            .property(
                Emailv31.MESSAGES,
                new JSONArray()
                    .put(
                        new JSONObject()
                            // FROM
                            .put(
                                Emailv31.Message.FROM,
                                new JSONObject()
                                    .put("Email", propMailFromEmail)
                                    .put("Name", propMailFromName)
                            )
                            // TO
                            .put(
                                Emailv31.Message.TO,
                                new JSONArray()
                                    .put(to)
                            )
                            // TEMPLATE
                            .put(Emailv31.Message.TEMPLATEID, getTemplateId(templateName))
                            .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                            // SUBJECT
                            .put(Emailv31.Message.SUBJECT, messageSubject)
                            // VARIABLES
                            .put(Emailv31.Message.VARIABLES, messageVars)
                    )
            );
        response = client.post(request);
        int status = response.getStatus();
        if(status != 200) {
            logger.error(String.format("%s Error sending message - status: %s; response: %s", LOG_PRFIX_SENDWITHTMPL, String.valueOf(status), response.getData().toString()));
        } else {
            logger.info(String.format("%s Message sent - status: %s; response: %s", LOG_PRFIX_SENDWITHTMPL, status, response.getData().toString()));
        }
    }

    private static int getTemplateId(String templateName) {
        int templateId = 0;
        switch (templateName) {
            case "signup":
                templateId = 450727;
                break;
            default:
                // Do nothing
        }
        return templateId;
    }

}
