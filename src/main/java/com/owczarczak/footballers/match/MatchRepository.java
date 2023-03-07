package com.owczarczak.footballers.match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    @Query("SELECT m FROM Match m WHERE m.nameOfReferee = :name")
    Match findByRefereeName(@Param("name") String name);
}