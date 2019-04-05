package com.example.starwarsboot.testData;

import com.example.starwarsboot.domains.CharacterModel;
import com.example.starwarsboot.domains.ResultPairModel;

import java.util.*;
import java.util.List;

public class TestData {




    public static TreeMap<String,Integer> createMap(){
        TreeMap<String,Integer> map = new TreeMap<>();
        map.put("Luke Skywalker",1);
        map.put("Anakin Skywalker",2);
        map.put("C-3PO",3);
        map.put("Yoda",4);
        map.put("Boba Fett",5);
        map.put("Wilhuff Tarkin",6);
        return map;
    }

    public static CharacterModel generateCharacter(Integer person){
        switch (person){
            case 1:
                CharacterModel character1= new CharacterModel();
                character1.setName("Luke Skywalker");
                character1.setMass("77");
                character1.setHeight("172");
                return character1;
            case 2:
                CharacterModel character2= new CharacterModel();
                character2.setName("Anakin Skywalker");
                character2.setMass("84");
                character2.setHeight("188");
                return character2;
            case 3:
                CharacterModel character3= new CharacterModel();
                character3.setName("C-3PO");
                character3.setMass("75");
                character3.setHeight("167");
                return character3;
            case 4:
                CharacterModel character4= new CharacterModel();
                character4.setName("Yoda");
                character4.setMass("17");
                character4.setHeight("66");
                return character4;
            case 5:
                CharacterModel character5= new CharacterModel();
                character5.setName("Boba Fett");
                character5.setMass("78.2");
                character5.setHeight("183");
                return character5;
            case 6:
                CharacterModel character6= new CharacterModel();
                character6.setName("Wilhuff Tarkin");
                character6.setMass("unknown");
                character6.setHeight("180");
                return character6;
        }
        return null;
    }

    public static ResultPairModel pairModel(){
        UUID uuid = UUID.fromString("f38af94e-5dfa-49b9-be9f-8fe3e0667d54");
        return new ResultPairModel(
                uuid,
                "Luke Skywalker", "77",
                "172",26.027582477014604,
                "Yoda","17","66",39.02662993572084);
    }

    public static List<ResultPairModel> pairModelList(){
        List<ResultPairModel> resultPairModels = Arrays.asList(
                new ResultPairModel("Luke Skywalker", "77", "172", 26.027582477014604, "Yoda", "17", "66", 39.02662993572084),
                new ResultPairModel("Luke Skywalker", "77", "172", 26.027582477014604, "C-3PO", "75", "167", 26.89232313815483),
                new ResultPairModel("Boba Fett", "78.2", "183", 23.35095105855654, "Yoda", "17", "66", 39.02662993572084),
                new ResultPairModel("C-3PO", "75", "167", 26.89232313815483, "Yoda", "17", "66", 39.02662993572084)
        );
        return resultPairModels;
    }
}
