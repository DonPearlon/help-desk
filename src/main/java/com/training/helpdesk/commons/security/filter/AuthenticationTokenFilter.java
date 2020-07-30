package com.training.helpdesk.commons.security.filter;

import com.training.helpdesk.commons.security.jwt.JwtUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Custom extension of the {@link OncePerRequestFilter}.
 *
 * @author Alexandr_Terehov
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER 
            = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

    private static final String TOKEN_PREFIX = "Bearer ";

    private  final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    public AuthenticationTokenFilter(
            final UserDetailsService userDetailsService, final JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exc) {
            LOGGER.error("Cannot set user authentication: {}", exc);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Parses Jwt token from HttpServletRequest.
     *
     * @param request
     *            {@link HttpServletRequest}.
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_PREFIX)) {
            return headerAuth.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}