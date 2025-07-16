package com.maosencantadas.config;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FF4jConfig {

    @Bean
    public FF4j ff4j() {
        FF4j ff4j = new FF4j();

        // Cria a feature flag "send-email" desabilitada por padrão
        if (!ff4j.exist("send-email")) {
            ff4j.createFeature(new Feature("send-email", true));
        }

        ff4j.setEnableAudit(true);
        ff4j.setAutocreate(true);

        return ff4j;
    }
}