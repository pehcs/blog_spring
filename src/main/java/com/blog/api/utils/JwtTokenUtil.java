package com.blog.api.utils;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {

    public static final String jwtSecret = "JAPALANA_NA_LAMA_LANA";
    private final String jwtIssuer = "com.blog";
    private final long EXPIRATION = 180000;
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public String generateAccessToken(String user) {
        return Jwts.builder()
                .setSubject(user)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)) // 1 week
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
                
        System.out.println("TESTADN" + claims);
        return claims.getSubject();
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature - {}");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token - {}");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token - {}");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token - {}");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty - {}");
        }
        return false;
    }

}