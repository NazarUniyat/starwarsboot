package com.example.starwarsboot.wires;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultResponseWire {
    private String firstPersonData;
    private String secondPersonData;
    private String bmi;

}
