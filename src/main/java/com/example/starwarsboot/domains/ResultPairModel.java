package com.example.starwarsboot.domains;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResultPairModel {

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

    public ResultPairModel(String person1, String weightPerson1, String heightPerson1, Double BMIPerson1, String person2, String weightPerson2, String heightPerson2, Double BMIPerson2) {
        this.person1 = person1;
        this.weightPerson1 = weightPerson1;
        this.heightPerson1 = heightPerson1;
        this.BMIPerson1 = BMIPerson1;
        this.person2 = person2;
        this.weightPerson2 = weightPerson2;
        this.heightPerson2 = heightPerson2;
        this.BMIPerson2 = BMIPerson2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultPairModel that = (ResultPairModel) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(person1, that.person1) &&
                Objects.equals(weightPerson1, that.weightPerson1) &&
                Objects.equals(heightPerson1, that.heightPerson1) &&
                Objects.equals(BMIPerson1, that.BMIPerson1) &&
                Objects.equals(person2, that.person2) &&
                Objects.equals(weightPerson2, that.weightPerson2) &&
                Objects.equals(heightPerson2, that.heightPerson2) &&
                Objects.equals(BMIPerson2, that.BMIPerson2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, person1, weightPerson1, heightPerson1, BMIPerson1, person2, weightPerson2, heightPerson2, BMIPerson2);
    }
}
