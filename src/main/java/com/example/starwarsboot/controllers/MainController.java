package com.example.starwarsboot.controllers;

import com.example.starwarsboot.Service.BMIResponseService;
import com.example.starwarsboot.Service.CharacterService;
import com.example.starwarsboot.component.CharactersMapper;
import com.example.starwarsboot.domains.BMIResultModel;
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
    private CharacterService characterService;
    private CharactersMapper charactersMappers;
    private BMIResponseService bmiResponseService;
    private String pageURL;

    @Autowired
    public MainController(CharacterModelRepository characterModelRepository, com.example.starwarsboot.repo.BMIResultModelRepository BMIResultModelRepository, CharacterService characterService, CharactersMapper charactersMappers, BMIResponseService bmiResponseService, String pageURL) {
        this.characterModelRepository = characterModelRepository;
        this.BMIResultModelRepository = BMIResultModelRepository;
        this.characterService = characterService;
        this.charactersMappers = charactersMappers;
        this.bmiResponseService = bmiResponseService;
        this.pageURL = pageURL;
    }

    @PostMapping("/add/{person1}/{person2}")
    @ResponseBody
    public ResponseEntity<List<BMIResultModel>> test(
            @PathVariable @Size(max = 25, message = "too long name in 1st param!") String person1,
            @PathVariable @Size(max = 25, message = "too long name in second param!") String person2
    ) {
        characterService.savePerson(person1, person2);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BMIResultModelRepository.findAll());
    }

    @GetMapping("/result/{uuid}")
    @ResponseBody
    public ResponseEntity<ResultResponseWire> returnResultOfCalculation(
            @PathVariable UUID uuid
    ){
        return ResponseEntity.ok().body(bmiResponseService.resultModel(uuid));
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
        return ResponseEntity.ok().body(characterService.getAll(pageable));
    }


}
