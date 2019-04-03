package com.example.starwarsboot.Service;

import com.example.starwarsboot.domains.CharacterModel;
import com.example.starwarsboot.domains.ResultPairModel;
import com.example.starwarsboot.wires.ResultResponseWire;

import java.util.UUID;

public interface PairService {

    ResultPairModel getResultFromDB(CharacterModel characterModel1, CharacterModel characterModel2);

    Double calculateBMIResult(CharacterModel model);

    UUID createPair(CharacterModel characterModel1, CharacterModel characterModel2);

    ResultResponseWire getComparedPair(UUID uuid);
}
