package com.example.starwarsboot.Utils;

import com.example.starwarsboot.domains.ResultPairModel;
import org.springframework.stereotype.Component;

@Component
public class BMIUtil {

    public String calculateBmiResult(Double index) {
        if (index < BMILevelsBounds.LESS_THEN_NORMAL) {
            return "this mass is too small";
        } else if (index >= BMILevelsBounds.PERFECT_LOWER_LIMIT && index <= BMILevelsBounds.PERFECT_UPPER_LIMIT) {
            return "Perfect mass!!";
        } else if (index >= BMILevelsBounds.EXCESS_MASS_LOWER_LIMIT && index <= BMILevelsBounds.EXCESS_MASS_UPPER_LIMIT) {
            return "little bit too much";
        } else if (index >= BMILevelsBounds.FIRST_DEGREE_OBESITY_LOWER_LIMIT && index <= BMILevelsBounds.FIRST_DEGREE_OBESITY_UPPER_LIMIT) {
            return "this person should do something with mass, to much";
        } else if (index >= BMILevelsBounds.SECOND_DEGREE_OBESITY_LOWER_LIMIT && index < BMILevelsBounds.SECOND_DEGREE_OBESITY_UPPER_LIMIT) {
            return "ALARM!!!!";
        } else return "...";

    }

    public String compareBmiResults(ResultPairModel ResultPairModel) {
        if (ResultPairModel.getBMIPerson1() > ResultPairModel.getBMIPerson2()) {
            return ResultPairModel.getPerson1() + " has a larger index then " + ResultPairModel.getPerson2();
        } else if (ResultPairModel.getBMIPerson1() < ResultPairModel.getBMIPerson2()) {
            return ResultPairModel.getPerson2() + " has a larger index then " + ResultPairModel.getPerson1();
        } else return "ooo, wonder! both characters have the same BMI. Maybe you have added 2 identical characters?";
    }

}
