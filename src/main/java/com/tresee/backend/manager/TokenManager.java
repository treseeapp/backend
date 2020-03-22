package com.tresee.backend.manager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:/application.properties")
public class TokenManager {

    @Autowired
    Environment environment;

    public String validateToken(String token) {

        try {

            Jwts.parser()
                    .setSigningKey(environment.getProperty("token.secret.key").getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            return "OK";

        } catch (ExpiredJwtException e) {
            return "EXPIRED";

        } catch (Exception e) {
            return "ERROR";
        }
    }

    public String getEmailbyToken(String token) {

        try {

            Claims claims = Jwts.parser()
                    .setSigningKey(environment.getProperty("token.secret.key").getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            return (String) claims.get("user");

        } catch (Exception e) {
            return "ERROR";
        }
    }
}
