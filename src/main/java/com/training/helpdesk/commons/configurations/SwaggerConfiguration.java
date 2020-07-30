package com.training.helpdesk.commons.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;

@Configuration 
@EnableSwagger2
public class SwaggerConfiguration {
    
    /**
     * @return {@link Docket} bean.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Help Desk")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/help-desk/**"))
                .build()
                .apiInfo(getApiInfo())
                .securitySchemes(Collections.singletonList(apiKey()))
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors
                .basePackage("com.training.helpdesk"))
                .paths(PathSelectors.any())
                .build();
    }
    
    /**
     * @return {@link ApiKey}.
     */
    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    }
    
    /**
     * @return {@link ApiInfo}.
     */
    private ApiInfo getApiInfo() {
        return new ApiInfo("Help Desk", "Help Desk Api Documentation", "1.0", "urn:tos",
                new Contact("", "", ""), "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", 
                new ArrayList<>());
    }
}
