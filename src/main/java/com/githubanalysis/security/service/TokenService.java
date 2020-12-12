package com.githubanalysis.security.service;

import com.githubanalysis.security.config.TokenConfig;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import static java.lang.Long.parseLong;

@Service
@Slf4j
public class TokenService {

    private TokenConfig tokenConfig;

    public TokenService(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();

        return generateToken(userDetailsImpl);
    }

    public String generateToken(UserDetailsImpl userDetailsImpl) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenConfig.getTokenExpiresInMilliSecs());

        return Jwts.builder()
                .setSubject(userDetailsImpl.getId().toString())
                .signWith(SignatureAlgorithm.HS512, tokenConfig.getTokenSecret())
                .setExpiration(expiryDate)
                .setIssuedAt(now)
                .compact();
    }

    Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenConfig.getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return parseLong(claims.getSubject());
    }

    boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(tokenConfig.getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            log.error("Invalid JWT!", ex);
        }
        return false;
    }

}
