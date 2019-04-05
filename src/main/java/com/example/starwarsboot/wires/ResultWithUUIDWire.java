package com.example.starwarsboot.wires;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultWithUUIDWire {
    public String status;
    public UUID uuid;
}
