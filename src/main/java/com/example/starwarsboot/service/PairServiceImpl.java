package com.example.starwarsboot.service;

import com.example.starwarsboot.utils.BMIUtil;
import com.example.starwarsboot.utils.PaginationUtil;
import com.example.starwarsboot.domains.ResultPairModel;
import com.example.starwarsboot.domains.CharacterModel;
import com.example.starwarsboot.exceptions.NoSuchUUIDException;
import com.example.starwarsboot.exceptions.UnknownPeronBodyParametersException;
import com.example.starwarsboot.repository.ResultPairModelRepository;
import com.example.starwarsboot.wires.PaginationWire;
import com.example.starwarsboot.wires.ResultResponseWire;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PairServiceImpl implements PairService{

    private static Logger log = LogManager.getLogger(PairServiceImpl.class);

    private ResultPairModelRepository resultPairModelRepository;
    private BMIUtil bmiUtil;
    @Value("${results.pagination.link}")
    private String pageURl;

    @Autowired
    public PairServiceImpl(ResultPairModelRepository resultPairModelRepository, BMIUtil bmiUtil) {
        this.resultPairModelRepository = resultPairModelRepository;
        this.bmiUtil = bmiUtil;
    }

    public ResultPairModel getResultFromDB(CharacterModel characterModel1, CharacterModel characterModel2) {
        ResultPairModel firstCombination = resultPairModelRepository.findByFirstAndSecondPerson(characterModel1.getName(), characterModel2.getName());
        ResultPairModel secondCombination = resultPairModelRepository.findByFirstAndSecondPerson(characterModel2.getName(), characterModel1.getName());
        if (firstCombination != null) {
            return firstCombination;
        } else if (secondCombination != null) {
            return secondCombination;
        }
        return null;
    }

    public Double calculateBMI(CharacterModel model){
        return Double.valueOf(model.getMass()) / Math.pow(Double.valueOf(model.getHeight()) / 100, 2);
    }

    public UUID createPair(CharacterModel characterModel1, CharacterModel characterModel2) {

        ResultPairModel ResultPairModel = getResultFromDB(characterModel1, characterModel2);

        if (ResultPairModel != null) {
            log.info("CALCULATION RESULT FROM DB");
            return ResultPairModel.getUuid();
        }

        double BMIPerson1;
        double BMIPerson2;
        try {
            BMIPerson1 = calculateBMI(characterModel1);
            BMIPerson2 = calculateBMI(characterModel2);
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

        resultPairModelRepository.save(ResultPairModel);
        log.info("CALCULATED RESULT NOT FROM DB, JUST SAVED");
        return ResultPairModel.getUuid();
    }

    public ResultResponseWire getComparedPair(UUID uuid) {

        ResultPairModel ResultPairModel;
        try {
            ResultPairModel = resultPairModelRepository.findById(uuid).get();
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
                        " result: " +
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
                        " result: " +
                        bmiUtil.calculateBmiResult(ResultPairModel.getBMIPerson2())
        );

        resultResponseWire.setBmi(bmiUtil.compareBmiResults(ResultPairModel));

        return resultResponseWire;
    }

    public PaginationWire getAllResults(Pageable pageable) {
        List<ResultPairModel> allByName = resultPairModelRepository.findAllResultPair(pageable);
        return PaginationUtil.getPaginationResult(allByName,pageable,this.pageURl);
    }
}
