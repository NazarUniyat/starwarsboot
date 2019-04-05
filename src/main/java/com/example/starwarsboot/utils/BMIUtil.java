package com.example.starwarsboot.utils;

import com.example.starwarsboot.domains.ResultPairModel;
import org.springframework.stereotype.Component;

@Component
public class BMIUtil {

    public String calculateBmiResult(Double index) {
        if (index < BMIBounds.LESS_THEN_NORMAL) {
            return "this mass is too small";
        } else if (index >= BMIBounds.PERFECT_LOWER_LIMIT && index <= BMIBounds.PERFECT_UPPER_LIMIT) {
            return "Perfect mass!!";
        } else if (index >= BMIBounds.EXCESS_MASS_LOWER_LIMIT && index <= BMIBounds.EXCESS_MASS_UPPER_LIMIT) {
            return "little bit too much";
        } else if (index >= BMIBounds.FIRST_DEGREE_OBESITY_LOWER_LIMIT && index <= BMIBounds.FIRST_DEGREE_OBESITY_UPPER_LIMIT) {
            return "this person should do something with mass, to much";
        } else if (index >= BMIBounds.SECOND_DEGREE_OBESITY_LOWER_LIMIT && index < BMIBounds.SECOND_DEGREE_OBESITY_UPPER_LIMIT) {
            return "ALARM!!!! Risk of health damage";
        } else return "ALARM!!!! High risk of health damage";

    }

    public String compareBmiResults(ResultPairModel ResultPairModel) {
        if (ResultPairModel.getBMIPerson1() > ResultPairModel.getBMIPerson2()) {
            return ResultPairModel.getPerson1() + " has a larger index then " + ResultPairModel.getPerson2();
        } else if (ResultPairModel.getBMIPerson1() < ResultPairModel.getBMIPerson2()) {
            return ResultPairModel.getPerson2() + " has a larger index then " + ResultPairModel.getPerson1();
        } else return "ooo, wonder! both characters have the same BMI. Maybe you have added 2 identical characters?";
    }

}
