package com.example.starwarsboot.domains;

import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@ToString
public class CharacterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID uuid;
    @Column
    private String name;
    @Column
    private String height;
    @Column
    private String mass;
    @Column
    private String hair_color;
    @Column
    private String skin_color;
    @Column
    private String eye_color;
    @Column
    private String birth_year;
    @Column
    private String gender;
    @Column
    private String homeworld;
    @Column(length = 500)
    private String[] films;
    @Column(length = 500)
    private String[] species;
    @Column(length = 500)
    private String[] vehicles;
    @Column(length = 500)
    private String[] starships;
    @Column
    private String created;
    @Column
    private String edited;
    @Column
    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterModel that = (CharacterModel) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(name, that.name) &&
                Objects.equals(height, that.height) &&
                Objects.equals(mass, that.mass) &&
                Objects.equals(hair_color, that.hair_color) &&
                Objects.equals(skin_color, that.skin_color) &&
                Objects.equals(eye_color, that.eye_color) &&
                Objects.equals(birth_year, that.birth_year) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(homeworld, that.homeworld) &&
                Arrays.equals(films, that.films) &&
                Arrays.equals(species, that.species) &&
                Arrays.equals(vehicles, that.vehicles) &&
                Arrays.equals(starships, that.starships) &&
                Objects.equals(created, that.created) &&
                Objects.equals(edited, that.edited) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(uuid, name, height, mass, hair_color, skin_color, eye_color, birth_year, gender, homeworld, created, edited, url);
        result = 31 * result + Arrays.hashCode(films);
        result = 31 * result + Arrays.hashCode(species);
        result = 31 * result + Arrays.hashCode(vehicles);
        result = 31 * result + Arrays.hashCode(starships);
        return result;
    }
}
