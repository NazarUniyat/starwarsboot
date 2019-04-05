package com.example.starwarsboot.wires;

import com.example.starwarsboot.component.CharactersMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.TreeMap;

@Getter
@Setter
public class HelloMappingWire {

    private List<String> instruction;
    private TreeMap<String, Integer> data;

    public HelloMappingWire(List<String> instruction, TreeMap<String, Integer> data) {
        this.instruction = instruction;
        this.data = data;
    }
}
