package com.example.starwarsboot.repository;

import com.example.starwarsboot.domains.CharacterModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CharacterModelRepository extends CrudRepository<CharacterModel, UUID> {

    @Query("from CharacterModel order by name asc")
    List<CharacterModel> findAllOrderByName(Pageable pageable);


    @Query("select p from CharacterModel p where p.name=:name")
    CharacterModel FindByPersonName(@Param("name") String name);
}
