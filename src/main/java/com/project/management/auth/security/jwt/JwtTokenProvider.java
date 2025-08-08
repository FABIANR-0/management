package com.project.management.auth.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Proveedor de tokens JWT.
 */
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${settings.auth.token-time}")
    private Integer tokenTime;

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(String userName){
        Map<String, Object> claims = new HashMap<>();

        //hora actual en milisegundos
        Date issuedAt = new Date(System.currentTimeMillis());

        //calcular timeToken a partir de hora actual
        Date expirationDate = new Date(issuedAt.getTime() + (tokenTime * 60 * 1000));

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    //validar el jwt recibido en la petición
    public boolean validJwt(String jwt){
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            return claims != null;
        } catch (Exception e) {
            log.error("Failed to validate token: {}", e.getMessage());
        }
        return false;
    }

    public String extractorUserName(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}