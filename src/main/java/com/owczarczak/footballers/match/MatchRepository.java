package com.owczarczak.footballers.match;

import com.owczarczak.footballers.footballer.Footballer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    @Query("SELECT m FROM Match m WHERE m.nameOfReferee =:name")
    List<Match> findByRefereeName(@Param("name") String name);
}