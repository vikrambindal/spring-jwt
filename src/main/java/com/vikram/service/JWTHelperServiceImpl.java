package com.vikram.service;

import com.vikram.domain.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JWTHelperServiceImpl implements JWTHelperService {

    // src: https://allkeysgenerator.com/
    private final String SIGN_KEY = "792442264529482B4D6251655468576D5A7134743777217A25432A462D4A614E";
    private final String AUDIENCE = "application";
    private final String AUTH_HEADER_ROLES = "roles";

    @Override
    public String generateToken(UserEntity userEntity) {
        return generateToken(Map.of(AUTH_HEADER_ROLES, userEntity.getRole().name()), userEntity.getEmail());
    }

    @Override
    public String generateToken(Map<String, String> claimMap, String email) {

        return Jwts
                .builder()
                .setClaims(claimMap)
                .setSubject(email)
                .setId(UUID.randomUUID().toString())
                .setAudience(AUDIENCE)
                .setIssuer(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        String tokenSubject = extractUsername(jwtToken);
        return tokenSubject.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    @Override
    public Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    @Override
    public <T> T verifyAndExtractClaims(String jwtToken, Function<Claims, T> function) {
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(jwtToken);
        return function.apply(claimsJws.getBody());
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> function) {
        Claims claims = extractAllClaims(jwtToken);
        return function.apply(claims);
    }

    private Key getSigningKey() {
        byte[] decode = Decoders.BASE64.decode(SIGN_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration).before(Date.from(Instant.now()));
    }

    private String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }
}
