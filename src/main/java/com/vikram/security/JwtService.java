package com.vikram.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // src: https://allkeysgenerator.com/
    private static final String SIGN_KEY = "792442264529482B4D6251655468576D5A7134743777217A25432A462D4A614E";

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public String generateToken(String email) {
        return generateToken(Map.of(), email);
    }

    public String generateToken(Map<String, String> claimMap, String email) {
        return Jwts
                .builder()
                .setClaims(claimMap)
                .setSubject(email)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        String tokenSubject = extractUsername(jwtToken);
        return tokenSubject.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> function) {
        Claims claims = extractAllClaims(jwtToken);
        return function.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] decode = Decoders.BASE64.decode(SIGN_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration).before(Date.from(Instant.now()));
    }
}
