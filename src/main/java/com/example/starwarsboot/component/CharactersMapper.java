package com.example.starwarsboot.component;

import com.example.starwarsboot.Service.SWAPIServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.TreeMap;


@EnableScheduling
public class CharactersMapper {
    private static Logger log = LogManager.getLogger(CharactersMapper.class);

    private TreeMap<String, Integer> data;


    private SWAPIServiceImpl swapiService;

    @Autowired
    public void setSwapiService(SWAPIServiceImpl swapiService) {
        this.swapiService = swapiService;
    }

    @Scheduled(fixedDelay = 1000000)
    private void mappedCharacters() {
        try {
            this.data = swapiService.createAllCharactersMap();
        } catch (NullPointerException e) {
            log.error(e.getMessage() + " or maybe there is no connection to external server");
        }
        log.info(data);
    }


    public TreeMap<String, Integer> getData() {
        return data;
    }

}
