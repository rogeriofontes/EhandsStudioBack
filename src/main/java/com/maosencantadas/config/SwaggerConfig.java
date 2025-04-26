package com.maosencantadas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration 
public class SwaggerConfig {
    @Bean 
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Maos Encantadas API")
                        .version("v1")
                        .description("API gerenciamento de clientes com Spring Boot 3"));
    }
}

