package com.maosencantadas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Configuração de arquivos estáticos (ex: imagens)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**");
               // .addResourceLocations("file:/C:/Users/pedro/Downloads/maosencantadas-back/uploads");
    }

    //coloquei pra ve se resolve a conexao cadstro do front
    //@Override
    //public void addCorsMappings(CorsRegistry registry) {
    //    registry.addMapping("/**").allowedOrigins("http://localhost:3000");
    //}


}
