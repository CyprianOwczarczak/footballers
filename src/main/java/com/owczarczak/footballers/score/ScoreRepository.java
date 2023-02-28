package com.owczarczak.footballers.score;

import com.owczarczak.footballers.footballer.Footballer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
//    @Query("SELECT s FROM score s ORDER BY s.minuteScored DESC")
//    List<Footballer> findAllByOrderByMinuteScored();
}
