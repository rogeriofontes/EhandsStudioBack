package com.maosencantadas.config;

import org.springframework.context.annotation.Configuration;
<<<<<<< HEAD
import org.springframework.web.servlet.config.annotation.CorsRegistry;
=======
>>>>>>> origin/main
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

<<<<<<< HEAD
    // Configuração de arquivos estáticos (ex: imagens)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**");
               // .addResourceLocations("file:/C:/Users/pedro/Downloads/maosencantadas-back/uploads");
    }

   
=======
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/C:/Users/pedro/Downloads/maosencantadas-back/uploads");
    }
>>>>>>> origin/main
}
