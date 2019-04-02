package com.example.starwarsboot.Utils;

import com.example.starwarsboot.domains.BMIResultModel;
import org.springframework.stereotype.Component;

@Component
public class BMIUtil {

    public String calculateBmiResult(Double index) {
        if (index < 18.5) {
            return "this mass is too small";
        } else if (index >= 18.5 && index <= 24.9) {
            return "Perfect mass!!";
        } else if (index >= 25 && index <= 29.9) {
            return "little bit too much";
        } else if (index >= 30 && index < 35) {
            return "this person should do something with mass, to much";
        } else if (index >= 35 && index < 39.9) {
            return "ALARM!!!!";
        } else return "...";

    }

    public String compareBmiResults(BMIResultModel BMIResultModel) {
        if (BMIResultModel.getBMIPerson1() > BMIResultModel.getBMIPerson2()) {
            return BMIResultModel.getPerson1() + " has a larger index then " + BMIResultModel.getPerson2();
        } else if (BMIResultModel.getBMIPerson1() < BMIResultModel.getBMIPerson2()) {
            return BMIResultModel.getPerson2() + " has a larger index then " + BMIResultModel.getPerson1();
        } else return "ooo, wonder! both characters have the same BMI. Maybe you have added 2 identical characters?";
    }

}
