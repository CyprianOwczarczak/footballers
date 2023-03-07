package com.owczarczak.footballers.score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Integer> {

    // Średnia liczba goli per mecz dla zawodnika
    @Query(value = """
            select scores.id, scores.name, scores.cnt as goals, matches.cnt as matches, (CAST (scores.cnt AS float) /matches.cnt) as averageGoals
            from
            (
            	select f.id, f.name, count(s.id) as cnt
            	from footballer f
            	join score s
            	on s.footballer_id = f.id
            	join match m
            	on s.match_id = m.id
            	group by f.id, f.name
            	order by cnt desc
            ) scores
            join\s
            (
            	select f.id, f.name, count(cr.id) as cnt
            	from footballer f
            	join club_representation_footballer crf
            	on f.id = crf.footballer_id
            	join club_representation cr
            	on crf.club_representation_id = cr.id
            	group by f.id, f.name
            	order by cnt desc
            ) matches
            on scores.id = matches.id
            """, nativeQuery = true)
    List<Object[]> getAvgGoalsPerMatchForFootballer();
}
