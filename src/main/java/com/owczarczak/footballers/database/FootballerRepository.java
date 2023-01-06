package com.owczarczak.footballers.database;

import com.owczarczak.footballers.model.Footballer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballerRepository extends JpaRepository<Footballer, Integer> {
}
