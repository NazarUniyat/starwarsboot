package com.example.starwarsboot.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySource("classpath:application.properties")
public class AppConfig {

//    @Value("${pagination.link}")
//    private String next;
//
//
//    @Bean
//    public String pageURL(){
//        return this.next;
//    }

    @Bean
    public CharactersMapper charactersMappers(){
        return new CharactersMapper();
    }
}
