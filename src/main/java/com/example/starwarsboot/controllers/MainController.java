package com.example.starwarsboot.controllers;

import com.example.starwarsboot.Service.PairServiceImpl;
import com.example.starwarsboot.Service.CharacterServiceImpl;
import com.example.starwarsboot.component.CharactersMapper;
import com.example.starwarsboot.domains.ResultPairModel;
import com.example.starwarsboot.repo.BMIResultModelRepository;
import com.example.starwarsboot.repo.CharacterModelRepository;
import com.example.starwarsboot.wires.PaginationWire;
import com.example.starwarsboot.wires.ResultResponseWire;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
@Validated
public class MainController {

    private CharacterModelRepository characterModelRepository;
    private BMIResultModelRepository BMIResultModelRepository;
    private CharacterServiceImpl characterServiceImpl;
    private CharactersMapper charactersMappers;
    private PairServiceImpl pairServiceImpl;

    @Autowired
    public MainController(CharacterModelRepository characterModelRepository, com.example.starwarsboot.repo.BMIResultModelRepository BMIResultModelRepository, CharacterServiceImpl characterServiceImpl, CharactersMapper charactersMappers, PairServiceImpl pairServiceImpl) {
        this.characterModelRepository = characterModelRepository;
        this.BMIResultModelRepository = BMIResultModelRepository;
        this.characterServiceImpl = characterServiceImpl;
        this.charactersMappers = charactersMappers;
        this.pairServiceImpl = pairServiceImpl;
    }

    @PostMapping("/add/{person1}/{person2}")
    @ResponseBody
    public ResponseEntity<List<ResultPairModel>> test(
            @PathVariable @Size(max = 25, message = "too long name in 1st param!") String person1,
            @PathVariable @Size(max = 25, message = "too long name in second param!") String person2
    ) {
        characterServiceImpl.savePerson(person1, person2);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BMIResultModelRepository.findAll());
    }

    @GetMapping("/result/{uuid}")
    @ResponseBody
    public ResponseEntity<ResultResponseWire> returnResultOfCalculation(
            @PathVariable UUID uuid
    ){
        return ResponseEntity.ok().body(pairServiceImpl.getComparedPair(uuid));
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity getResp(){
        return ResponseEntity.ok().body(charactersMappers.getData());
    }

    @GetMapping("/alldb")
    @ResponseBody
    public ResponseEntity getAllDb(){
        return ResponseEntity.ok().body(characterModelRepository.findAll());
    }

    @GetMapping("/allResults")
    @ResponseBody
    public ResponseEntity<PaginationWire> getAllResults(
            @PageableDefault(value = 3) Pageable pageable
    ){
        return ResponseEntity.ok().body(characterServiceImpl.getAll(pageable));
    }


}
