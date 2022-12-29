package com.owczarczak.footballers.repository;

import com.owczarczak.footballers.model.Footballer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface FootballerRepository {

    Footballer addFootballer(Footballer entity);

    Optional<Footballer> getById(int playerId);

    List<Footballer> getAllPlayers();

    List<Footballer> getByClubName(String clubName);

    List<Footballer> getHighest(int numberOfHighest);

    int calculateMeanHeight();
}
