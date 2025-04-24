package com.maosencantadas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MaosencantadasApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(MaosencantadasApplication.class, args);
    }

 //   @Override
//    public void addCorsMappings(CorsRegistry registry) {  // perguntar pro Rogerio resolver o pom.xml
 //       registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000") // colocar o caminho do front aqui
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
}
