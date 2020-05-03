package com.tresee.backend.manager.seguridad;

import com.tresee.backend.enitty.Usuario;
import com.tresee.backend.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenManager {

    @Autowired
    private Environment environment;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public String createTokenEmail(String email) {
        return Jwts.builder()
                .claim("email", email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(environment.getProperty("jwt.secret")))
                .compact();
    }

    public String validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret").getBytes())
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
                    .setSigningKey(environment.getProperty("jwt.secret").getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            return (String) claims.get("email");

        } catch (Exception e) {
            return "ERROR";
        }
    }

    public String getRol(String token) {

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret").getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            return (String) claims.get("rol");

        } catch (Exception e) {
            return "ERROR";
        }
    }

    public Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret").getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    public Usuario getUsuarioFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            Long idTokenUsuario = Long.parseLong(claims.get("idusuario").toString());
            return usuarioRepository.findByIdusuario(idTokenUsuario);
        } catch (Exception e) {
            return null;
        }
    }
}
