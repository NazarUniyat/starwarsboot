package com.example.starwarsboot.Service;

import com.example.starwarsboot.domains.CharacterModel;

import java.util.TreeMap;

public interface SWAPIService {
    CharacterModel getCharacterUsingApi(Integer personId);

    TreeMap<String, Integer> createAllCharactersMap();
}
