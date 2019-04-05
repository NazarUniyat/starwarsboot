package com.example.starwarsboot.wires;
import com.example.starwarsboot.domains.CharacterModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class PaginationWire {

    private String next;

    private List<?> result;
}
