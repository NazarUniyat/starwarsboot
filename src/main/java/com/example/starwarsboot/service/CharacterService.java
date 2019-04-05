package com.example.starwarsboot.service;

import com.example.starwarsboot.domains.CharacterModel;
import com.example.starwarsboot.wires.HelloMappingWire;
import com.example.starwarsboot.wires.PaginationWire;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CharacterService {
    CharacterModel getCharacterFromCache(Integer personId);

    CharacterModel getCharacterFromDB(String personName);

    CharacterModel getAndSaveCharacterAfterAllChecks(Integer personId, String personName);

    UUID savePerson(String personName1, String personName2);

    PaginationWire getAllCharacters(Pageable pageable);

    HelloMappingWire helloMapping();
}
