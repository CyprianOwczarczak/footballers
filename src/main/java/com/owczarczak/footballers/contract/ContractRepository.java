package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.club.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    //This query gets Contracts and orders them by salary
    @Query("SELECT c FROM Contract c ORDER BY c.salary DESC")
    Page<Contract> findAllByOrderBySalaryDesc(Pageable pageable);

//- Średnia długość kontraktów piłkarzy w danym zespole
    //Fixme how to calculate the average Instant
    @Query("""
            select f.name, avg(con.contractEnd - con.contractStart)
            from Footballer f
            join Contract con
            on con.footballer = f
            join Club c
            on con.club = :searchedClub
            group by f.name
            """)
    Instant getMeanLenghtOfContractsInClub(@Param("searchedClub") Club club);

    //- Średnia liczba bramek zdobytych przez piłkarzy w trakcie kontraktu trwającego mniej niż np. rok
//    select new java.lang.Double(avg(f.goals))


//    @Query("""
//            select (avg(f.goals))
//            from Footballer f
//            join Contract c
//            on c.footballer = f.id
//            where (c.contractStart between :startDate and :endDate)
//            """)
//    List getMeanGoalsScoredByFootballersDuringContractLastingLessThanYear(@Param("startDate") Instant startDate,
//                                                                                  @Param("endDate") Instant endDate);


//    Średnia liczba goli per mecz dla zawodnika
    //Fixme returns empty rows
    @Query("""
            select f.name, count(s.id)/count(m.id)
            from Footballer f
            join Score s
            on s.footballer = f
            join Match m
            on s.match = m
            group by f.id
            """)
    List<Double> getAvgGoalsPerMatchForFootballer();
}