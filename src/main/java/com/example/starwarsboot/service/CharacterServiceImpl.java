package com.example.starwarsboot.service;
import com.example.starwarsboot.exceptions.QuarryingSourcesException;
import com.example.starwarsboot.utils.PaginationUtil;
import com.example.starwarsboot.component.CharactersMapper;
import com.example.starwarsboot.domains.CharacterModel;
import com.example.starwarsboot.repository.CharacterModelRepository;
import com.example.starwarsboot.wires.HelloMappingWire;
import com.example.starwarsboot.wires.PaginationWire;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CharacterServiceImpl implements CharacterService{


    private static Logger log = LogManager.getLogger(CharacterServiceImpl.class);

    private Cache<Integer, CharacterModel> cache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();

    public Cache<Integer, CharacterModel> getCache() {
        return cache;
    }

    @Value("${characters.pagination.link}")
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

    public CharacterModel getCharacterFromCache(Integer personId) {
        return cache.getIfPresent(personId);
    }

    public CharacterModel getCharacterFromDB(String personName) {
        return characterModelRepository.FindByPersonName(personName);
    }


    public CharacterModel getAndSaveCharacterAfterAllChecks(Integer personId, String personName) {
        CharacterModel personToSaveAndCalculate;

        if (getCharacterFromCache(personId) != null) {
            personToSaveAndCalculate = getCharacterFromCache(personId);
            log.info("FROM CACHE");
        } else if (getCharacterFromDB(personName) != null) {
            personToSaveAndCalculate = getCharacterFromDB(personName);
            cache.put(personId, personToSaveAndCalculate);
            log.info("FROMDB AND PACKED TO CACHE");
        } else {
            personToSaveAndCalculate = swapiService.getCharacterUsingApi(personId);
            cache.put(personId, personToSaveAndCalculate);
            characterModelRepository.save(personToSaveAndCalculate);
            log.info("FROM API! JUST SAVED TO DB AND CACHE");
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
            throw new QuarryingSourcesException("maybe there is no person with name " + personName1 + " or " + personName2);
        }

        CharacterModel firstPersonToSaveAndCalculate = getAndSaveCharacterAfterAllChecks(person1Id, personName1);
        CharacterModel secondPersonToSaveAndCalculate = getAndSaveCharacterAfterAllChecks(person2Id, personName2);


        return pairServiceImpl.createPair(firstPersonToSaveAndCalculate, secondPersonToSaveAndCalculate);
    }


    public PaginationWire getAllCharacters(Pageable pageable) {
        List<CharacterModel> allByName = characterModelRepository.findAllOrderByName(pageable);
        return PaginationUtil.getPaginationResult(allByName,pageable,this.pageURl);
    }

    public HelloMappingWire helloMapping(){
        File file;
        try {
            file = ResourceUtils.getFile("classpath:static/readme.txt");
        } catch (FileNotFoundException e) {
            List<String> strings = Arrays.asList(
                    "ops, sorry but instruction is temporarily unavailable",
                    "but if you see a list of characters try to do something=)"
            );
            return new HelloMappingWire(strings,charactersMappers.getData());
        }

        List<String> strings = new ArrayList<>();
        try(Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()){
                String instruction = scanner.nextLine();
                strings.add(instruction);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new HelloMappingWire(strings,charactersMappers.getData());
    }


}
