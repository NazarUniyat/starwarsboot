package com.example.starwarsboot.service;

import com.example.starwarsboot.domains.CharacterModel;
import com.example.starwarsboot.exceptions.QuarryingSourcesException;
import com.example.starwarsboot.wires.AllPersonsWire;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

@Service
public class SWAPIServiceImpl implements SWAPIService {

    private static Logger log = LogManager.getLogger(SWAPIServiceImpl.class);

    public CharacterModel getCharacterUsingApi(Integer personId) {

        HttpResponse<JsonNode> jsonResponse;
        try {
            jsonResponse = Unirest.get("https://swapi.co/api/people/" + personId + "/")
                    .asJson();
        } catch (UnirestException e) {
            throw new QuarryingSourcesException("maybe some problems with internet connection");
        }
        jsonResponse.getStatus();
        String s = jsonResponse.getBody().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        CharacterModel characterModel;
        try {
            characterModel = objectMapper.readValue(s, CharacterModel.class);

        } catch (IOException e) {
            throw new QuarryingSourcesException("maybe some problems with internet connection");
        }
        return characterModel;
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

    public TreeMap<String, Integer> createAllCharactersMap() {

        TreeMap<String, Integer> personList = new TreeMap<>();

        String apiToCall = "https://swapi.co/api/people/";

        AllPersonsWire response;
        do {
            response = getResponse(apiToCall);
            List<CharacterModel> results = response.getResults();
            for (CharacterModel result : results) {
                personList.put(
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
}
