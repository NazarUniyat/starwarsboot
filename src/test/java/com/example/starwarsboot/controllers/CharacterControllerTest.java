package com.example.starwarsboot.controllers;

import com.example.starwarsboot.domains.ResultPairModel;
import com.example.starwarsboot.exceptions.QuarryingSourcesException;
import com.example.starwarsboot.repository.CharacterModelRepository;
import com.example.starwarsboot.repository.ResultPairModelRepository;
import com.example.starwarsboot.testData.TestData;
import com.example.starwarsboot.component.CharactersMapper;
import com.example.starwarsboot.service.CharacterServiceImpl;
import com.example.starwarsboot.service.SWAPIServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CharacterServiceImpl characterService;

    @Autowired
    ResultPairModelRepository pairModelRepository;

    @Autowired
    CharacterModelRepository characterModelRepository;

    @MockBean
    SWAPIServiceImpl swapiService;

    @MockBean
    CharactersMapper charactersMapper;

    @Test
    public void givenURI_whenHello_thenReturnCorrectResponseThatContainInstruction() throws Exception {


        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }


    @Test
    public void givenURIWithTwoNamesThatHaveMassAndHeight_whenCalculate_thenReturnCorrectResponseThatContainUUIDAndStatus() throws Exception {

        when(charactersMapper.getData()).thenReturn(TestData.createMap());
        when(swapiService.getCharacterUsingApi(1)).thenReturn(TestData.generateCharacter(1));
        when(swapiService.getCharacterUsingApi(2)).thenReturn(TestData.generateCharacter(2));

        this.mockMvc.perform(post("/calculate/Luke Skywalker/Anakin Skywalker")).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("202 ACCEPTED"));
    }

    @Test
    public void givenURIWithTwoNamesButOneNotExist_whenCalculate_thenReturnQuarryingSourcesException() throws Exception {

        when(charactersMapper.getData()).thenReturn(TestData.createMap());
        when(swapiService.getCharacterUsingApi(1)).thenReturn(TestData.generateCharacter(1));
        when(swapiService.getCharacterUsingApi(2)).thenReturn(TestData.generateCharacter(2));

        this.mockMvc.perform(post("/calculate/Luke Skywalker/Anadkin Lol")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("oops, something went wrong. : maybe there is no person with name Luke Skywalker or Anadkin Lol"));
    }

    @Test
    public void givenURIWithTwoNamesButOneCharacterWithoutMass_whenCalculate_thenReturnUnknownPeronBodyParametersException() throws Exception {

        when(charactersMapper.getData()).thenReturn(TestData.createMap());
        when(swapiService.getCharacterUsingApi(1)).thenReturn(TestData.generateCharacter(1));
        when(swapiService.getCharacterUsingApi(6)).thenReturn(TestData.generateCharacter(6));

        this.mockMvc.perform(post("/calculate/Luke Skywalker/Wilhuff Tarkin")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Sorry, but there is no information about the mass or height of one of the characters"));
    }

    @Test
    public void givenURIWithTwoNamesButOneIsTooLong_whenCalculate_thenReturnConstraintViolationException() throws Exception {

        when(charactersMapper.getData()).thenReturn(TestData.createMap());
        when(swapiService.getCharacterUsingApi(1)).thenReturn(TestData.generateCharacter(1));
        when(swapiService.getCharacterUsingApi(2)).thenReturn(TestData.generateCharacter(2));

        this.mockMvc.perform(post("/calculate/Luke Skywalker/AnakinAnakinAnakinAnakinAnakin")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("calculate.character2: too long name in second param!"));
    }

    @Test
    public void givenURIWithTwoCorrectNameButNoInternetConnection_whenCalculate_thenReturnQuarryingSourcesException() throws Exception {
        when(charactersMapper.getData()).thenReturn(TestData.createMap());
        when(swapiService.getCharacterUsingApi(5)).thenThrow(new QuarryingSourcesException("maybe some problems with internet connection"));
        when(swapiService.getCharacterUsingApi(2)).thenReturn(TestData.generateCharacter(2));

        this.mockMvc.perform(post("/calculate/Boba Fett/Anakin Skywalker")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("oops, something went wrong. : maybe some problems with internet connection"));

    }

    @Test
    public void givenURIResults_whenGetAllResults_thenReturnPaginationWireResponseWithLinkThatContainNextPage() throws Exception {
        List<ResultPairModel> resultPairModels = TestData.pairModelList();
        for (ResultPairModel resultPairModel : resultPairModels) {
            pairModelRepository.save(resultPairModel);
        }
        this.mockMvc.perform(get("/results")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.next").value("http://localhost:8080/results?page=1"))
                .andExpect(jsonPath("$.result").isNotEmpty())
                .andExpect(jsonPath("$.result").isArray());
        pairModelRepository.deleteAll();
    }

    @Test
    public void givenURIResults_whenGetAllResults_thenReturnPaginationWireResponseWithoutLinkThatContainNextPage() throws Exception {
        List<ResultPairModel> resultPairModels = TestData.pairModelList();
            pairModelRepository.save(resultPairModels.get(0));
            pairModelRepository.save(resultPairModels.get(1));

        this.mockMvc.perform(get("/results")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.next").isEmpty())
                .andExpect(jsonPath("$.result").isNotEmpty())
                .andExpect(jsonPath("$.result").isArray());
        pairModelRepository.deleteAll();
    }

    @Test
    public void givenURIResults_whenGetAllResults_thenReturnEmptyPaginationWireResponse() throws Exception {

        this.mockMvc.perform(get("/results")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.next").isEmpty())
                .andExpect(jsonPath("$.result").isEmpty())
                .andExpect(jsonPath("$.result").isArray());
        pairModelRepository.deleteAll();
    }

    @Test
    public void givenURICharacters_whenGetAllCharacters_thenReturnPaginationWireResponseWithLinkThatContainNextPage() throws Exception {

        characterModelRepository.deleteAll();

        characterModelRepository.save(TestData.generateCharacter(1));
        characterModelRepository.save(TestData.generateCharacter(2));
        characterModelRepository.save(TestData.generateCharacter(3));
        characterModelRepository.save(TestData.generateCharacter(4));

        this.mockMvc.perform(get("/characters")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.next").value("http://localhost:8080/characters?page=1"))
                .andExpect(jsonPath("$.result").isNotEmpty())
                .andExpect(jsonPath("$.result").isArray());
        characterModelRepository.deleteAll();
    }

    @Test
    public void givenURICharacters_whenGetAllCharacters_thenReturnPaginationWireResponseWithoutLinkThatContainNextPage() throws Exception {

        characterModelRepository.deleteAll();

        characterModelRepository.save(TestData.generateCharacter(1));
        characterModelRepository.save(TestData.generateCharacter(2));
        characterModelRepository.save(TestData.generateCharacter(3));
        characterModelRepository.save(TestData.generateCharacter(4));

        this.mockMvc.perform(get("/characters?page=1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.next").isEmpty())
                .andExpect(jsonPath("$.result").isNotEmpty())
                .andExpect(jsonPath("$.result").isArray());
        characterModelRepository.deleteAll();
    }

    @Test
    public void givenURICharacters_whenGetAllCharacters_thenReturnEmptyPaginationWireResponse() throws Exception {

        characterModelRepository.deleteAll();
        this.mockMvc.perform(get("/characters")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.next").isEmpty())
                .andExpect(jsonPath("$.result").isEmpty())
                .andExpect(jsonPath("$.result").isArray());
        characterModelRepository.deleteAll();
    }


}