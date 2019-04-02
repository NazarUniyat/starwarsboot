package com.example.starwarsboot.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BMIResultModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @Column
    private String person1;
    @Column
    private String weightPerson1;
    @Column
    private String heightPerson1;
    @Column
    private Double BMIPerson1;
    @Column
    private String person2;
    @Column
    private String weightPerson2;
    @Column
    private String heightPerson2;
    @Column
    private Double BMIPerson2;

    public BMIResultModel(String person1, String weightPerson1, String heightPerson1, Double BMIPerson1, String person2, String weightPerson2, String heightPerson2, Double BMIPerson2) {
        this.person1 = person1;
        this.weightPerson1 = weightPerson1;
        this.heightPerson1 = heightPerson1;
        this.BMIPerson1 = BMIPerson1;
        this.person2 = person2;
        this.weightPerson2 = weightPerson2;
        this.heightPerson2 = heightPerson2;
        this.BMIPerson2 = BMIPerson2;
    }
}
