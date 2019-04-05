package com.example.starwarsboot.config;

import com.example.starwarsboot.component.CharactersMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public CharactersMapper charactersMappers(){
        return new CharactersMapper();
    }
}
