package com.owczarczak.footballers.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    @Query("""
            select c
            from Contract c
            where c.footballer.id = :fId
            """)
    List<Contract> getListOfContractsForSpecificFootballer(@Param("fId") int fId);

    //- Średnia długość kontraktów piłkarzy w danym zespole
    //Długość kontraktów w dniach, Średnia w danym klubie
    @Query(value = """
            select
            avg(cast(c.contract_end as date) - cast(c.contract_start as date))
            from contract c
            where c.club_id = :clubId
            group by c.club_id
            """, nativeQuery = true)
    BigDecimal getMeanLenghtOfContractsInClub(@Param("clubId") int clubId);

    //This query gets Contracts and orders them by salary
    @Query("SELECT c FROM Contract c ORDER BY c.salary DESC")
    Page<Contract> getAllByOrderBySalaryDesc(Pageable pageable);
}
