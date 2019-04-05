package com.example.starwarsboot.repository;

import com.example.starwarsboot.domains.ResultPairModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResultPairModelRepository extends JpaRepository<ResultPairModel, UUID> {

    @Query("from ResultPairModel order by BMIPerson1 asc")
    List<ResultPairModel> findAllResultPair(Pageable pageable);

    @Query("select r from ResultPairModel r where r.person1=:name1 and r.person2=:name2")
    ResultPairModel findByFirstAndSecondPerson(
            @Param("name1") String name1,
            @Param("name2") String name2
    );
}
