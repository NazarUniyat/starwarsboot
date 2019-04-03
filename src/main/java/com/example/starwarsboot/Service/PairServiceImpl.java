package com.example.starwarsboot.Service;

import com.example.starwarsboot.Utils.BMIUtil;
import com.example.starwarsboot.domains.ResultPairModel;
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
public class PairServiceImpl implements PairService{


    private BMIResultModelRepository bmiResultModelRepository;
    private BMIUtil bmiUtil;

    @Autowired
    public PairServiceImpl(BMIResultModelRepository bmiResultModelRepository, BMIUtil bmiUtil) {
        this.bmiResultModelRepository = bmiResultModelRepository;
        this.bmiUtil = bmiUtil;
    }

    public ResultPairModel getResultFromDB(CharacterModel characterModel1, CharacterModel characterModel2) {
        ResultPairModel firstCombination = bmiResultModelRepository.findByFirstAndSecondPerson(characterModel1.getName(), characterModel2.getName());
        ResultPairModel secondCombination = bmiResultModelRepository.findByFirstAndSecondPerson(characterModel2.getName(), characterModel1.getName());
        if (firstCombination != null) {
            return firstCombination;
        } else if (secondCombination != null) {
            return secondCombination;
        }
        return null;
    }

    public Double calculateBMIResult(CharacterModel model){
        return (double) valueOf(model.getMass()) / Math.pow(Double.valueOf(model.getHeight()) / 100, 2);
    }

    public UUID createPair(CharacterModel characterModel1, CharacterModel characterModel2) {

        ResultPairModel ResultPairModel = getResultFromDB(characterModel1, characterModel2);

        if (ResultPairModel != null) {
            System.out.println("CALCULATION RESULT FROM DB");
            return ResultPairModel.getUuid();
        }

        double BMIPerson1;
        double BMIPerson2;
        try {
            BMIPerson1 = calculateBMIResult(characterModel1);
            BMIPerson2 = calculateBMIResult(characterModel2);
        } catch (NumberFormatException e) {
            throw new UnknownPeronBodyParametersException();
        }

        ResultPairModel = new ResultPairModel(
                characterModel1.getName(),
                characterModel1.getMass(),
                characterModel1.getHeight(),
                BMIPerson1,
                characterModel2.getName(),
                characterModel2.getMass(),
                characterModel2.getHeight(),
                BMIPerson2);

        bmiResultModelRepository.save(ResultPairModel);
        System.out.println("CALCULATED RESULT NOT FROM DB, JUST SAVED");
        return ResultPairModel.getUuid();
    }

    public ResultResponseWire getComparedPair(UUID uuid) {

        ResultPairModel ResultPairModel;
        try {
            ResultPairModel = bmiResultModelRepository.findById(uuid).get();
        } catch (NoSuchElementException e) {
            throw new NoSuchUUIDException();
        }

        ResultResponseWire resultResponseWire = new ResultResponseWire();

        resultResponseWire.setFirstPersonData(
                ResultPairModel.getPerson1() +
                        " has mass - " +
                        ResultPairModel.getWeightPerson1() +
                        " and height - " +
                        ResultPairModel.getHeightPerson1() +
                        " and BMI " +
                        ResultPairModel.getBMIPerson1() +
                        " is " +
                        bmiUtil.calculateBmiResult(ResultPairModel.getBMIPerson1())
        );
        resultResponseWire.setSecondPersonData(
                ResultPairModel.getPerson2() +
                        " has mass - " +
                        ResultPairModel.getWeightPerson2() +
                        " and height - " +
                        ResultPairModel.getHeightPerson2() +
                        " and BMI " +
                        ResultPairModel.getBMIPerson2() +
                        " is " +
                        bmiUtil.calculateBmiResult(ResultPairModel.getBMIPerson2())
        );

        resultResponseWire.setBmi(bmiUtil.compareBmiResults(ResultPairModel));

        return resultResponseWire;
    }
}
