package com.example.starwarsboot.repo;

import com.example.starwarsboot.domains.ResultPairModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BMIResultModelRepository extends JpaRepository<ResultPairModel, UUID> {

    @Query("select r from ResultPairModel r where r.person1=:name1 and r.person2=:name2")
    ResultPairModel findByFirstAndSecondPerson(
            @Param("name1") String name1,
            @Param("name2") String name2
    );
}
