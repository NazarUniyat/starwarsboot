package com.example.starwarsboot.repository;

import com.example.starwarsboot.domains.CharacterModel;
import com.example.starwarsboot.testData.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CharacterModelRepositoryTest {

    private Pageable pageable;

    @Autowired
    CharacterModelRepository  characterModelRepository;

    @Test
    public void givenName_whenFindByPersonName_thenReturnCharacterModelByName(){
        CharacterModel characterModel = TestData.generateCharacter(1);
        CharacterModel save = characterModelRepository.save(characterModel);
        CharacterModel fromDB = characterModelRepository.FindByPersonName(characterModel.getName());

        assertEquals(save,fromDB);
        characterModelRepository.deleteAll();
    }

    @Test
    public void givenPageable_whenFindAllOrderByName_thenReturnListOfCharacters(){
        List<CharacterModel> characterModels = Arrays.asList(
                TestData.generateCharacter(1),
                TestData.generateCharacter(2),
                TestData.generateCharacter(3),
                TestData.generateCharacter(4)
        );
        for (CharacterModel characterModel : characterModels) {
            characterModelRepository.save(characterModel);
        }

        List<CharacterModel> allOrderByName = characterModelRepository.findAllOrderByName(pageable);

        assertTrue(!allOrderByName.isEmpty());
        assertEquals(allOrderByName.size(),characterModels.size());
        characterModelRepository.deleteAll();

    }

}