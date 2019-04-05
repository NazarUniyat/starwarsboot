package com.example.starwarsboot.controllers;

import com.example.starwarsboot.service.PairServiceImpl;
import com.example.starwarsboot.service.CharacterServiceImpl;
import com.example.starwarsboot.wires.HelloMappingWire;
import com.example.starwarsboot.wires.PaginationWire;
import com.example.starwarsboot.wires.ResultResponseWire;
import com.example.starwarsboot.wires.ResultWithUUIDWire;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@RestController
@Log4j2
@Validated
public class CharacterController {

    private CharacterServiceImpl characterServiceImpl;
    private PairServiceImpl pairServiceImpl;

    @Autowired
    public CharacterController(CharacterServiceImpl characterServiceImpl, PairServiceImpl pairServiceImpl) {
        this.characterServiceImpl = characterServiceImpl;
        this.pairServiceImpl = pairServiceImpl;
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<HelloMappingWire> hello(){
        return ResponseEntity.ok().body(characterServiceImpl.helloMapping());
    }

    @PostMapping("/calculate/{character1}/{character2}")
    @ResponseBody
    public ResponseEntity<ResultWithUUIDWire> calculate(
            @PathVariable @Size(max = 25, message = "too long name in 1st param!") String character1,
            @PathVariable @Size(max = 25, message = "too long name in second param!") String character2
    ) {
        UUID uuid = characterServiceImpl.savePerson(character1, character2);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new ResultWithUUIDWire(HttpStatus.ACCEPTED.toString(),uuid));
    }

    @GetMapping("/getResult/{uuid}")
    @ResponseBody
    public ResponseEntity<ResultResponseWire> returnResultOfCalculation(
            @PathVariable UUID uuid
    ){
        return ResponseEntity.ok().body(pairServiceImpl.getComparedPair(uuid));
    }


    @GetMapping("/characters")
    @ResponseBody
    public ResponseEntity<PaginationWire> getAllCharacters(
            @PageableDefault(value = 3) Pageable pageable
    ){
        return ResponseEntity.ok().body(characterServiceImpl.getAllCharacters(pageable));
    }

    @GetMapping("/results")
    @ResponseBody
    public ResponseEntity<PaginationWire> getAllResults(
            @PageableDefault(value = 3) Pageable pageable
    ){
        return ResponseEntity.ok().body(pairServiceImpl.getAllResults(pageable));
    }



}
