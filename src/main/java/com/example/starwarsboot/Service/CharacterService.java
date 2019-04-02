package com.example.starwarsboot.Service;

import com.example.starwarsboot.Utils.BMIUtil;
import com.example.starwarsboot.component.CharactersMapper;
import com.example.starwarsboot.domains.CharacterModel;
import com.example.starwarsboot.exceptions.QuarryingDatabaseException;
import com.example.starwarsboot.exceptions.QuarryingSourcesException;
import com.example.starwarsboot.repo.CharacterModelRepository;
import com.example.starwarsboot.wires.PaginationWire;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CharacterService {


    Cache<Integer, CharacterModel> cache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();

    private CharacterModelRepository characterModelRepository;
    private CharactersMapper charactersMappers;
    private String pageURl;
    private BMIResponseService bmiResponseService;

    @Autowired
    public CharacterService(CharacterModelRepository characterModelRepository, CharactersMapper charactersMappers, String pageURl, BMIResponseService bmiResponseService) {
        this.characterModelRepository = characterModelRepository;
        this.charactersMappers = charactersMappers;
        this.pageURl = pageURl;
        this.bmiResponseService = bmiResponseService;
    }

    private CharacterModel getPersonFromCache(Integer personId) {
        return cache.getIfPresent(personId);
    }

    private CharacterModel getPersonFromDB(String personName) {
        return characterModelRepository.findByPersonName(personName);
//        return characterModelRepository.findByPersonName(personName.replaceAll("(.)([A-Z])", "$1 $2"));
    }

    private CharacterModel getPersonUsingApi(Integer personId) {

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
        cache.put(personId, characterModel);
        return characterModel;
    }

    private CharacterModel getPersonAfterAllChecks(Integer personId, String personName) {
        CharacterModel personToSaveAndCalculate;

        if (getPersonFromCache(personId) != null) {
            personToSaveAndCalculate = getPersonFromCache(personId);
            System.out.println("FROM CACHE");
        } else if (getPersonFromDB(personName) != null) {
            personToSaveAndCalculate = getPersonFromDB(personName);
            cache.put(personId, personToSaveAndCalculate);
            System.out.println("FROMDB AND PACKED TO CACHE");
        } else {
            personToSaveAndCalculate = getPersonUsingApi(personId);
            characterModelRepository.save(personToSaveAndCalculate);
            System.out.println("FROM API! JUST SAVED TO DB AND CACHE");
        }

        return personToSaveAndCalculate;
    }


    public UUID savePerson(String personName1, String personName2) {

        int person1Id;
        int person2Id;
        try {
            person1Id = charactersMappers.getData().get(personName1);
            person2Id = charactersMappers.getData().get(personName2);
        } catch (NullPointerException e) {
            throw new QuarryingDatabaseException("there is no person with name " + personName1 + " or " + personName2);
        }

        CharacterModel firstPersonToSaveAndCalculate = getPersonAfterAllChecks(person1Id, personName1);
        CharacterModel secondPersonToSaveAndCalculate = getPersonAfterAllChecks(person2Id, personName2);


        return bmiResponseService.calculateBMIForSavedPerson(firstPersonToSaveAndCalculate, secondPersonToSaveAndCalculate);
    }



    public PaginationWire getAll(Pageable pageable) {
        List<CharacterModel> allByName = characterModelRepository.findAllByName(pageable);
        PaginationWire paginationWire = new PaginationWire();
        paginationWire.setNext(pageURl + pageable.next().getPageNumber());
        paginationWire.setCharacterModels(allByName);
        if (allByName.size() < pageable.next().getPageSize()) {
            paginationWire.setNext(null);
            return paginationWire;
        }
        return paginationWire;
    }


}
