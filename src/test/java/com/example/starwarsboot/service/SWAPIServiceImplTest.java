package com.example.starwarsboot.service;

import com.example.starwarsboot.component.CharactersMapper;
import com.example.starwarsboot.domains.CharacterModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SWAPIServiceImplTest {

    @Autowired
    CharactersMapper charactersMappers;

    @Autowired
    SWAPIServiceImpl swapiService;

    @Test
    public void getCharacterUsingApi() {
        CharacterModel lukeSkywalker = swapiService.getCharacterUsingApi(1);

        assertEquals(lukeSkywalker.getName(),"Luke Skywalker");
    }

}