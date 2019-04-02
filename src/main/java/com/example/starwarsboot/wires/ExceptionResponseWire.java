package com.example.starwarsboot.wires;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ExceptionResponseWire {
    private String status;
    private String message;

}
