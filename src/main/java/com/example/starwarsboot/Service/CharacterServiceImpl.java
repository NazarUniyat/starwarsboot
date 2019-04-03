package com.example.starwarsboot.Service;
import com.example.starwarsboot.component.CharactersMapper;
import com.example.starwarsboot.domains.CharacterModel;
import com.example.starwarsboot.exceptions.QuarryingDatabaseException;
import com.example.starwarsboot.repo.CharacterModelRepository;
import com.example.starwarsboot.wires.PaginationWire;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CharacterServiceImpl implements CharacterService{


    private Cache<Integer, CharacterModel> cache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();
    @Value("${pagination.link}")
    private String pageURl;


    private CharacterModelRepository characterModelRepository;
    private CharactersMapper charactersMappers;
    private PairServiceImpl pairServiceImpl;
    private SWAPIServiceImpl swapiService;

    @Autowired
    public CharacterServiceImpl(CharacterModelRepository characterModelRepository, CharactersMapper charactersMappers, PairServiceImpl pairServiceImpl, SWAPIServiceImpl swapiService) {
        this.characterModelRepository = characterModelRepository;
        this.charactersMappers = charactersMappers;
        this.pairServiceImpl = pairServiceImpl;
        this.swapiService = swapiService;
    }

    public CharacterModel getPersonFromCache(Integer personId) {
        return cache.getIfPresent(personId);
    }

    public CharacterModel getPersonFromDB(String personName) {
        return characterModelRepository.findByPersonName(personName);
//        return characterModelRepository.findByPersonName(personName.replaceAll("(.)([A-Z])", "$1 $2"));
    }


    public CharacterModel getPersonAfterAllChecks(Integer personId, String personName) {
        CharacterModel personToSaveAndCalculate;

        if (getPersonFromCache(personId) != null) {
            personToSaveAndCalculate = getPersonFromCache(personId);
            System.out.println("FROM CACHE");
        } else if (getPersonFromDB(personName) != null) {
            personToSaveAndCalculate = getPersonFromDB(personName);
            cache.put(personId, personToSaveAndCalculate);
            System.out.println("FROMDB AND PACKED TO CACHE");
        } else {
            personToSaveAndCalculate = swapiService.getCharacterUsingApi(personId);
            cache.put(personId, personToSaveAndCalculate);
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


        return pairServiceImpl.createPair(firstPersonToSaveAndCalculate, secondPersonToSaveAndCalculate);
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
