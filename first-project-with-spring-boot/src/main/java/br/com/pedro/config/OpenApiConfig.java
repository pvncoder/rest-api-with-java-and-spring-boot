/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@Configuration
public class OpenApiConfig {

    // The SpringIOC Container search all configuration files (e.g. xml, json, @Bean, etc)
    // It's an object instantiated, mounted and managed by SpringIOC Container
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("RESTful API with Java 19 and Spring Boot 3.0.3")
                .version("v1")
                .description("First project with Spring Boot")
                .termsOfService("https://swagger.io/")
                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
