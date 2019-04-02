package com.example.starwarsboot.component;

import com.example.starwarsboot.wires.AllPersonsWire;
import com.example.starwarsboot.domains.CharacterModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

@EnableScheduling
public class CharactersMapper {

    private static Logger log = LogManager.getLogger(CharactersMapper.class);

    private TreeMap<String, Integer> data;

    public TreeMap<String, Integer> getData() {
        return data;
    }


    private AllPersonsWire getResponse(String apiToCall) {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse<JsonNode> jsonResponse = null;

        try {
            jsonResponse = Unirest.get(apiToCall).asJson();
        } catch (UnirestException e) {
            log.error(e.getMessage());
        }

        AllPersonsWire allPersonsWire = null;

        try {
            allPersonsWire = objectMapper
                    .readValue(jsonResponse.getBody().toString(), AllPersonsWire.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info(allPersonsWire);
        return allPersonsWire;
    }

    private TreeMap<String, Integer> createData() {

        TreeMap<String, Integer> personList = new TreeMap<>();

        String apiToCall = "https://swapi.co/api/people/";

        AllPersonsWire response;
        do {
            response = getResponse(apiToCall);
            List<CharacterModel> results = response.getResults();
            for (CharacterModel result : results) {
                personList.put(
//                        result.getName().replaceAll(" ", ""),
                        result.getName(),
                        Integer.valueOf(result.getUrl().replaceAll("[^0-9]", ""))
                );
            }
            if (response.getNext() == null) {
                break;
            }
            apiToCall = response.getNext();

        } while (true);

        return personList;
    }

    @Scheduled(fixedDelay = 1000000)
    private void mappedCharacters() {
        try {
            this.data = createData();
        } catch (NullPointerException e) {
            log.error(e.getMessage() + " or maybe there is no connection to external server");
        }
        log.info(data);
    }


}
