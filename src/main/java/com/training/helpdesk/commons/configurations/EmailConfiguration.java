package com.training.helpdesk.commons.configurations;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

@Configuration
@PropertySource("classpath:mail/emailconfig.properties")
public class EmailConfiguration {

    private static final Logger LOGGER 
            = LoggerFactory.getLogger(EmailConfiguration.class);
    private final StandardPBEStringEncryptor passwordEncryptor;
    private final EmailProperties emailProperties;
    
    /**
     * Constructor.
     *
     * @param applicationContext
     *            {@link ApplicationContext}.
     * @param environment
     *            {@link Environment}.
     * @param passwordEncryptor
     *            {@link StandardPBEStringEncryptor}.
     */   
    public EmailConfiguration(final ApplicationContext applicationContext, 
            final Environment environment, final StandardPBEStringEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
        this.emailProperties = new EmailProperties(applicationContext, environment);
    }
    
    /**
     * @return {@link JavaMailSender} bean.
     */
    @Bean
    public JavaMailSender mailSender() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailProperties.getHost());
        mailSender.setPort(emailProperties.getPort());
        mailSender.setProtocol(emailProperties.getProtocol());
        mailSender.setUsername(emailProperties.getUsername());
        mailSender.setPassword(
                passwordEncryptor.decrypt(emailProperties.getPassword()));
        mailSender.setJavaMailProperties(emailProperties.getJavaMailProperties());
        return mailSender;
    }
    
    /**
     * @return {@link TemplateEngine} bean.
     */
    @Bean
    public TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        return templateEngine;
    }
    
    /**
     * @return implementation of the {@link ITemplateResolver}.
     */
    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(Integer.valueOf(EmailProperties.ORDER));
        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
        templateResolver.setPrefix("/mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(EmailProperties.EMAIL_TEMPLATE_ENCODING);
        templateResolver.setCacheable(false);
        return templateResolver;
    }
    
    /**
     * Inner class contains provides access to the 'email' properties.
     */
    private class EmailProperties {
    
        private static final String JAVA_MAIL_FILE = "classpath:mail/javamail.properties";    
        private static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";
        private static final int ORDER = 2;
        private static final String HOST = "mail.server.host";
        private static final String PORT = "mail.server.port";
        private static final String PROTOCOL = "mail.server.protocol";
        private static final String USERNAME = "mail.server.username";
        private static final String PASSWORD = "mail.server.password";
    
        private final Environment environment;
        private final ApplicationContext applicationContext;
    
        /**
         * Constructor.
         *
         * @param applicationContext
         *            {@link ApplicationContext}.
         * @param environment
         *            {@link Environment}.
         */   
        private EmailProperties(
                final ApplicationContext applicationContext, final Environment environment) {
            this.environment = environment;
            this.applicationContext = applicationContext;
        }
        
        /**
         * @return 'host' property.
         */
        private String getHost() {
            return this.environment.getProperty(HOST);
        }

        /**
         * @return 'port' property.
         */
        private Integer getPort() {
            return Integer.parseInt(this.environment.getProperty(PORT));
        }

        /**
         * @return 'protocol' property.
         */
        private String getProtocol() {
            return this.environment.getProperty(PROTOCOL);
        }

        /**
         * @return 'username' property.
         */
        private String getUsername() {
            return this.environment.getProperty(USERNAME);
        }

        /**
         * @return 'password' property.
         */
        private String getPassword() {
            return this.environment.getProperty(PASSWORD);
        }

        /**
         * @return Java mail properties {@link Properties}.
         */
        private Properties getJavaMailProperties() {
            Properties javaMailProperties = new Properties();
            try {
                javaMailProperties.load(
                        this.applicationContext.getResource(JAVA_MAIL_FILE).getInputStream());
            } catch (IOException exc) {
                LOGGER.error("Error with reading properties file");
            }
            return  javaMailProperties;
        }
    }
}