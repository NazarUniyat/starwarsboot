package com.example.starwarsboot.Service;

import com.example.starwarsboot.Utils.BMIUtil;
import com.example.starwarsboot.domains.BMIResultModel;
import com.example.starwarsboot.domains.CharacterModel;
import com.example.starwarsboot.exceptions.NoSuchUUIDException;
import com.example.starwarsboot.exceptions.UnknownPeronBodyParametersException;
import com.example.starwarsboot.repo.BMIResultModelRepository;
import com.example.starwarsboot.wires.ResultResponseWire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

import static java.lang.Integer.valueOf;

@Service
public class BMIResponseService {


    private BMIResultModelRepository bmiResultModelRepository;
    private BMIUtil bmiUtil;

    @Autowired
    public BMIResponseService(BMIResultModelRepository bmiResultModelRepository, BMIUtil bmiUtil) {
        this.bmiResultModelRepository = bmiResultModelRepository;
        this.bmiUtil = bmiUtil;
    }

    private BMIResultModel getResultFromDB(CharacterModel characterModel1, CharacterModel characterModel2) {
        BMIResultModel firstCombination = bmiResultModelRepository.findByFirstAndSecondPerson(characterModel1.getName(), characterModel2.getName());
        BMIResultModel secondCombination = bmiResultModelRepository.findByFirstAndSecondPerson(characterModel2.getName(), characterModel1.getName());
        if (firstCombination != null) {
            return firstCombination;
        } else if (secondCombination != null) {
            return secondCombination;
        }
        return null;
    }

    public UUID calculateBMIForSavedPerson(CharacterModel characterModel1, CharacterModel characterModel2) {

        BMIResultModel BMIResultModel = getResultFromDB(characterModel1, characterModel2);

        if (BMIResultModel != null) {
            System.out.println("CALCULATION RESULT FROM DB");
            return BMIResultModel.getUuid();
        }

        double BMIPerson1;
        double BMIPerson2;
        try {
            BMIPerson1 = (double) valueOf(characterModel1.getMass()) / Math.pow(Double.valueOf(characterModel1.getHeight()) / 100, 2);
            BMIPerson2 = (double) valueOf(characterModel2.getMass()) / Math.pow(Double.valueOf(characterModel2.getHeight()) / 100, 2);
        } catch (NumberFormatException e) {
            throw new UnknownPeronBodyParametersException();
        }

        BMIResultModel = new BMIResultModel(
                characterModel1.getName(),
                characterModel1.getMass(),
                characterModel1.getHeight(),
                BMIPerson1,
                characterModel2.getName(),
                characterModel2.getMass(),
                characterModel2.getHeight(),
                BMIPerson2);

        bmiResultModelRepository.save(BMIResultModel);
        System.out.println("CALCULATED RESULT NOT FROM DB, JUST SAVED");
        return BMIResultModel.getUuid();
    }

    public ResultResponseWire resultModel(UUID uuid) {

        BMIResultModel BMIResultModel;
        try {
            BMIResultModel = bmiResultModelRepository.findById(uuid).get();
        } catch (NoSuchElementException e) {
            throw new NoSuchUUIDException();
        }

        ResultResponseWire resultResponseWire = new ResultResponseWire();

        resultResponseWire.setFirstPersonData(
                BMIResultModel.getPerson1() +
                        " has mass - " +
                        BMIResultModel.getWeightPerson1() +
                        " and height - " +
                        BMIResultModel.getHeightPerson1() +
                        " and BMI " +
                        BMIResultModel.getBMIPerson1() +
                        " is " +
                        bmiUtil.calculateBmiResult(BMIResultModel.getBMIPerson1())
        );
        resultResponseWire.setSecondPersonData(
                BMIResultModel.getPerson2() +
                        " has mass - " +
                        BMIResultModel.getWeightPerson2() +
                        " and height - " +
                        BMIResultModel.getHeightPerson2() +
                        " and BMI " +
                        BMIResultModel.getBMIPerson2() +
                        " is " +
                        bmiUtil.calculateBmiResult(BMIResultModel.getBMIPerson2())
        );

        resultResponseWire.setBmi(bmiUtil.compareBmiResults(BMIResultModel));

        return resultResponseWire;
    }
}
