package com.tresee.backend.manager;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class CorreoManager {

    @Autowired
    private Environment environment;

    public void sendEmailActivateAccount(String to, String subject, String url, String nombreProfesor) throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post(environment.getProperty("MAILGUN_BASE_URL") + environment.getProperty("MAILGUN_DOMAIN") + "/messages")
                .basicAuth("api", environment.getProperty("MAILGUN_API_KEY"))
                .field("from", "no-reply@tresee.app")
                .field("to", to)
                .field("subject", subject)
                .field("template", "activation_account")
                .field("v:profesor", nombreProfesor)
                .field("v:linkButton", url)
                .asJson();
    }

}
