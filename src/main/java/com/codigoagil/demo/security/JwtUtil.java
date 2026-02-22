package com.codigoagil.demo.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JwtUtil {
    
    // En un proyecto real de producción, esto iría en el application.properties
    private static final String SECRET_KEY = "MiClaveSecretaSuperSeguraParaElPortafolioHelpdesk";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    public String generarToken(String email, String rol) {
        return JWT.create()
                .withSubject(email)
                .withClaim("rol", rol)
                .withIssuedAt(new Date())
                // El token expira en 10 horas
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) 
                .sign(ALGORITHM);
    }

    public String extraerEmail(String token) {
        return JWT.require(ALGORITHM)
                .build()
                .verify(token)
                .getSubject();
    }

    public boolean validarToken(String token) {
        try {
            JWT.require(ALGORITHM).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}