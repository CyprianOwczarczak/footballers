package com.owczarczak.footballers.repository;

import com.owczarczak.footballers.model.Footballer;

import java.util.List;
import java.util.Optional;

public interface FootballerRepository {
    Footballer addFootballer(Footballer entity);

    Optional<Footballer> getById();

    List<Footballer> getAllPlayers();

    List<Footballer> getByClubName();

    List<Footballer> getHighest();

    int calculateMeanHeight();
}
