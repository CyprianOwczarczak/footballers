package com.owczarczak.footballers.contract;

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
//    @Query("""
//            select
//            from Contract c
//            """)
//    double getMeanLenghtOfContractsInClub(Club club);

    //- Średnia liczba bramek zdobytych przez piłkarzy w trakcie kontraktu trwającego mniej niż np. rok
//    select new java.lang.Double(avg(f.goals))

//
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
    @Query("""
            select f.name
            from Footballer
            join Score s
            on s.footballer = f
            group by f.name
            """)
    List<Object[]> getAvgGoalsPerMatchForFootballer();


//    @Query("""
//            select avg(c.salary), c.footballer.name
//            from Contract c
//            group by c.footballer.name
//            """)
//    List<Object[]> getAvgValue();
}