package com.example.starwarsboot.domains;

import lombok.*;

import javax.persistence.*;
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


}
