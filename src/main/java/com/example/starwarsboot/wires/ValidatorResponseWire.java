package com.example.starwarsboot.wires;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ValidatorResponseWire {
    private String status;
    private String message;
}
