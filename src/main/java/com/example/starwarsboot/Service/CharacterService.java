package com.example.starwarsboot.Service;

import com.example.starwarsboot.domains.CharacterModel;
import com.example.starwarsboot.wires.PaginationWire;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CharacterService {
    CharacterModel getPersonFromCache(Integer personId);

    CharacterModel getPersonFromDB(String personName);

    CharacterModel getPersonAfterAllChecks(Integer personId, String personName);

    UUID savePerson(String personName1, String personName2);

    PaginationWire getAll(Pageable pageable);
}
