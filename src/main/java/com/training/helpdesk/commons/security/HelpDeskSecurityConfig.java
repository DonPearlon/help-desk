package com.training.helpdesk.commons.security;

import com.training.helpdesk.commons.security.filter.AuthenticationTokenFilter;
import com.training.helpdesk.commons.security.jwt.JwtUtils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Application Security configuration.  
 *
 * @author Alexandr_Terehov
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "com.training.helpdesk.security")
public class HelpDeskSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
  
    private static final int MIN_LOG = 10;
    
    /**
     * Constructor
     * 
     * @param userDetailsService
     *            object implements {@link UserDetailsService} interface to set.
     * @param jwtUtils
     *            instance of the {@link JwtUtils} class to set.            
     */
    public HelpDeskSecurityConfig(
            final UserDetailsService userDetailsService, final JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }
    
    /**
     * @return {@link AuthenticationProvider}.
     */
    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(MIN_LOG));
        return provider;
    }
   
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .addFilterBefore(authenticationTokenFilter(),
                    UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling().authenticationEntryPoint(getAuthEntryPoint()).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/help-desk/**").authenticated();
    }
    
    /**
     * @return {@link HelpDeskJwtAuthenticationEntryPoint}.
     */
    @Bean
    public HelpDeskJwtAuthenticationEntryPoint getAuthEntryPoint() {
        return new  HelpDeskJwtAuthenticationEntryPoint();
    }
    
    /**
     * @return {@link AuthenticationTokenFilter}.
     */
    public AuthenticationTokenFilter authenticationTokenFilter() {
        return new AuthenticationTokenFilter(userDetailsService, jwtUtils);
    }
    
    /**
     * @return {@link AuthenticationManager}.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}