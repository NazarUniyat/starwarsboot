package com.example.starwarsboot.service;

import com.example.starwarsboot.domains.CharacterModel;
import com.example.starwarsboot.domains.ResultPairModel;
import com.example.starwarsboot.wires.PaginationWire;
import com.example.starwarsboot.wires.ResultResponseWire;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PairService {

    ResultPairModel getResultFromDB(CharacterModel characterModel1, CharacterModel characterModel2);

    Double calculateBMI(CharacterModel model);

    UUID createPair(CharacterModel characterModel1, CharacterModel characterModel2);

    ResultResponseWire getComparedPair(UUID uuid);

    PaginationWire getAllResults(Pageable pageable);
}
