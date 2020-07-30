package com.training.helpdesk.commons.security.jwt;

import com.training.helpdesk.commons.security.HelpDeskUserPrincipal;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Provides operations with Jwt token.
 *
 * @author Alexandr_Terehov
 */
@Component
public class JwtUtils {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(JwtUtils.class);
    private static final int EXPIRATION_WEEKS = 2;

    @Value("com.training.helpdesk.jwt.Secret")
    private String jwtSecret;
    
    /**
     * Generates Jwt token from {@link Authentication}.
     *
     * @param authentication
     *            {@link Authentication}.
     * @return token string.           
     */
    public String generateJwtToken(Authentication authentication) {
        HelpDeskUserPrincipal userPrincipal 
                = (HelpDeskUserPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .claim("authorities", userPrincipal.getAuthorities())
                .setIssuedAt(new java.util.Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(EXPIRATION_WEEKS)))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    /**
     * Retrieves name of the user from jwt token string.
     *
     * @param token
     *            jwt token string.
     * @return name of the user.           
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    /**
     * Validates jwt token.
     *
     * @param authToken
     *            jwt token string.
     * @return result of the validation.           
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException exc) {
            LOGGER.error("Invalid JWT token: {}", exc.getMessage());
        } catch (ExpiredJwtException exc) {
            LOGGER.error("JWT token is expired: {}", exc.getMessage());
        } catch (UnsupportedJwtException exc) {
            LOGGER.error("JWT token is unsupported: {}", exc.getMessage());
        } catch (IllegalArgumentException exc) {
            LOGGER.error("JWT claims string is empty: {}", exc.getMessage());
        }
        return false;
    }
}