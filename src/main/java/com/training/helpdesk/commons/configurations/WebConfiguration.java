package com.training.helpdesk.commons.configurations;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

import javax.validation.Validator;


@Configuration
@EnableWebMvc
@EnableCaching
@ComponentScan(basePackages = "com.training.helpdesk.*")
@PropertySource("classpath:/swagger/documentation.properties")
public class WebConfiguration implements WebMvcConfigurer {
    private static final int MAX_UPLOAD_SIZE = 20971520;
    private static final int MAX_IN_MEMORY_SIZE = 1048576;
    private static final String FRONT_URL = "http://localhost:3000";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
    
    /**
     * @return {@link CacheManager}.
     */
    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }
    
    /**
     * @return {@link EhCacheManagerFactoryBean}.
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
        factory.setConfigLocation(new ClassPathResource("ehcache.xml"));
        factory.setShared(true);
        return factory;
    }
    
    /**
     * @return {@link Validator}.
     */
    @Bean
    public Validator localValidatorFactoryBean() { 
        return new LocalValidatorFactoryBean();
    }
    
    /**
     * @return {@link MethodValidationPostProcessor}.
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
    
    /**
     * @return {@link CommonsMultipartResolver}.
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
        commonsMultipartResolver.setMaxInMemorySize(MAX_IN_MEMORY_SIZE);
        return commonsMultipartResolver;
    }
    
    /**
     * @return {@link StandardPBEStringEncryptor}.
     */
    @Bean
    public StandardPBEStringEncryptor passwordEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("SimplePassword");
        return encryptor;
    }
    
    /**
     * @return {@link CorsConfigurationSource}.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Collections.singletonList(FRONT_URL));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList(
                "X-Requested-With","Origin","Content-Type","Accept","Authorization"));
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers",
                "Authorization, x-xsrf-token, Access-Control-Allow-Headers,"
                + " Origin, Accept, X-Requested-With, " 
                + "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}