package com.example.starwarsboot.controllers;

import com.example.starwarsboot.component.CharactersMapper;
import com.example.starwarsboot.repository.ResultPairModelRepository;
import com.example.starwarsboot.service.CharacterServiceImpl;
import com.example.starwarsboot.service.PairService;
import com.example.starwarsboot.service.SWAPIServiceImpl;
import com.example.starwarsboot.testData.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CharacterControllerTestWithMockRepository {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CharacterServiceImpl characterService;
    @Autowired
    PairService pairService;

    @MockBean
    SWAPIServiceImpl swapiService;
    @MockBean
    CharactersMapper charactersMapper;
    @MockBean
    ResultPairModelRepository pairModelRepository;

    @Test
    public void givenURIWithUUIDToGetResult_whenReturnResultOfCalculation_thenReturnResponseWithCalculation() throws Exception {
        when(pairModelRepository.findById(TestData.pairModel().getUuid())).thenReturn(java.util.Optional.of(TestData.pairModel()));

        this.mockMvc.perform(get("/getResult/f38af94e-5dfa-49b9-be9f-8fe3e0667d54")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.firstPersonData")
                        .value("Luke Skywalker has mass - 77 and height - 172 and BMI 26.027582477014604 result: little bit too much"))
                .andExpect(jsonPath("$.secondPersonData")
                        .value("Yoda has mass - 17 and height - 66 and BMI 39.02662993572084 result: ALARM!!!! Risk of health damage"))
                .andExpect(jsonPath("$.bmi").value("Yoda has a larger index then Luke Skywalker"));
        pairModelRepository.deleteAll();
    }

    @Test
    public void givenURIWithNonExistentUUIDToGetResult_whenReturnResultOfCalculation_thenReturnResponseWithCalculation() throws Exception {
        when(pairModelRepository.findById(TestData.pairModel().getUuid())).thenReturn(java.util.Optional.of(TestData.pairModel()));

        this.mockMvc.perform(get("/getResult/894e09f4-71b2-43e2-bf28-cad31495680c")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Unfortunately you have inserted wrong or non-existent uuid"));
        pairModelRepository.deleteAll();
    }

}