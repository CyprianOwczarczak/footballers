package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.club.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    //This query gets Contracts and orders them by salary
    @Query("SELECT c FROM Contract c ORDER BY c.salary DESC")
    Page<Contract> findAllByOrderBySalaryDesc(Pageable pageable);

//- Średnia długość kontraktów piłkarzy w danym zespole
    @Query("""
            select c
            from Contract c
            """)
    double getMeanLenghtOfContractsInClub(Club club);

//- Średnia liczba bramek zdobytych przez piłkarzy w trakcie kontraktu trwającego mniej niż np. rok
//    @Query("""
//            select f
//            from footballer f
//            """)
//    double getMeanGoalsScoredByFootballersDuringContractLastingLessThanYear();

//    SELECT pub FROM Publisher pub JOIN pub.magazines mag WHERE pub.revenue > 1000000
}