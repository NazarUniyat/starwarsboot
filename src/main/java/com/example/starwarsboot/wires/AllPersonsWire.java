package com.example.starwarsboot.wires;

import com.example.starwarsboot.domains.CharacterModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AllPersonsWire {
    private int count;
    private String next;
    private String previous;
    private List<CharacterModel> results;
}
