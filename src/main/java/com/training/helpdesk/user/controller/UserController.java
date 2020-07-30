package com.training.helpdesk.user.controller;

import com.training.helpdesk.commons.security.HelpDeskUserPrincipal;
import com.training.helpdesk.commons.security.jwt.JwtUtils;
import com.training.helpdesk.user.dto.UserCredentialsDto;
import com.training.helpdesk.user.dto.UserDto;
import com.training.helpdesk.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Api(tags = "1. Authentication")
public class UserController {
    private static final Logger LOGGER 
            = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    
    /**
     * The constructor of the class.
     *
     * @param userService - {@link UserService}.
     * @param authenticationManager - {@link AuthenticationManager}
     * @param jwtUtils - {@link JwtUtils}
     */
    public UserController(final UserService userService, 
            final AuthenticationManager authenticationManager, final JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }
    
    /**
     * Returns {@link UserDto} and jwt token in response header.
     *
     * @param userCredentials
     *            - {@link UserCredentialsDto}.
     * @return {@link ResponseEntity}
     */
    @PostMapping(value = "/sign-in", 
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Sign in", notes = "${UserController.signInJwt.notes}")
    public ResponseEntity<UserDto> signInJwt(
            @Valid @RequestBody final UserCredentialsDto userCredentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    userCredentials.getEmail(), userCredentials.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        HelpDeskUserPrincipal userPrincipal 
                = (HelpDeskUserPrincipal) authentication.getPrincipal();
        UserDto userDto = userService.getUserDtoById(userPrincipal.getUserId());
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("Authorization", jwt);
        LOGGER.info(SecurityContextHolder.getContext().getAuthentication().getName()
                .concat(" : has been successfully authenticated."));
        return new ResponseEntity<>(userDto, responseHeader, HttpStatus.OK);
    }
}