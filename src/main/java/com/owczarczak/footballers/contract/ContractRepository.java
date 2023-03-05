package com.owczarczak.footballers.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    @Query("""
            select c
            from Contract c
            join Footballer f
            on c.footballer = f
            where f.id = :fId
            """)
    List<Object> getListOfContractsForSpecificFootballer(@Param("fId") int fId);

    //- Średnia długość kontraktów piłkarzy w danym zespole
    //Długość kontraktów w dniach, Średnia w danym klubie
    //todo Add the club search clause (nativeQuery), how to calculate the average date
    @Query(value = """
            select cl.name, (c.contract_end - c.contract_start)
            from contract c
            join club cl
            on c.club_id = c.id
            group by c.id, cl.name
            """, nativeQuery = true)
    List<Object> getMeanLenghtOfContractsInClub(int clubId);

//    CAST ('100' AS INTEGER);
//    AVG(TIMESTAMPDIFF(HOUR, start_date, end_date))/

    //    Średnia liczba goli per mecz dla zawodnika
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
    List<Object> getAvgGoalsPerMatchForFootballer();


    //This query gets Contracts and orders them by salary
    @Query("SELECT c FROM Contract c ORDER BY c.salary DESC")
    Page<Contract> findAllByOrderBySalaryDesc(Pageable pageable);
}