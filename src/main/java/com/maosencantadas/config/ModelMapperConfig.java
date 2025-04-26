package com.maosencantadas.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(false) // não pula nulos
                .setFieldMatchingEnabled(true) // match nos campos mesmo que não tenha getter
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE); // acessar até campos privados
        return modelMapper;
    }
}