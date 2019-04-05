package com.example.starwarsboot.repository;

import com.example.starwarsboot.domains.ResultPairModel;
import com.example.starwarsboot.testData.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ResultPairModelRepositoryTest {

    private Pageable pageable;

    @Autowired
    ResultPairModelRepository pairModelRepository;


    @Test
    public void givenName_whenFindByPersonName_thenReturnCharacterModelByName(){
        ResultPairModel resultPairModel = TestData.pairModel();
        ResultPairModel save = pairModelRepository.save(resultPairModel);
        ResultPairModel fromDB = pairModelRepository.findByFirstAndSecondPerson(resultPairModel.getPerson1(), resultPairModel.getPerson2());
        assertEquals(save,fromDB);
        pairModelRepository.deleteAll();
    }

    @Test
    public void givenPageable_whenFindAllResultPair_thenReturnListOfPairModels(){
        List<ResultPairModel> resultPairModels = TestData.pairModelList();
        for (ResultPairModel resultPairModel : resultPairModels) {
            pairModelRepository.save(resultPairModel);
        }
        List<ResultPairModel> allResultPair = pairModelRepository.findAllResultPair(pageable);
        assertTrue(!allResultPair.isEmpty());
        assertEquals(resultPairModels.size(),allResultPair.size());
        pairModelRepository.deleteAll();
    }

}