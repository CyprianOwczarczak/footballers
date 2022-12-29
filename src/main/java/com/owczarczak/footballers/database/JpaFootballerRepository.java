package com.owczarczak.footballers.database;

import com.owczarczak.footballers.model.Footballer;
import com.owczarczak.footballers.repository.FootballerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface JpaFootballerRepository extends FootballerRepository, JpaRepository<Footballer, Integer> {

    @Override
    Footballer addFootballer(Footballer entity);

    @Override
    Optional<Footballer> getById(int playerId);

    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Integer id);

    List<Footballer> getAllPlayers();

    @Override
    List<Footballer> getByClubName(String clubName);

    @Override
    List<Footballer> getHighest(int numberOfHighest);

    @Override
    int calculateMeanHeight();
}
