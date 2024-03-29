package com.owczarczak.footballers.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("""
            select c
            from Contract c
            where c.footballer.id = :fId
            """)
    List<Contract> getListOfContractsForSpecificFootballer(@Param("fId") Long fId);

    //The lenght of a contract in days, average in a given club
    @Query(value = """
            select
            avg(cast(c.contract_end as date) - cast(c.contract_start as date))
            from contract c
            where c.club_id = :clubId
            group by c.club_id
            """, nativeQuery = true)
    BigDecimal getMeanLenghtOfContractsInClub(@Param("clubId") Long clubId);

    //This query gets Contracts and orders them by salary
    @Query("SELECT c FROM Contract c ORDER BY c.salary DESC")
    Page<Contract> getAllByOrderBySalaryDesc(Pageable pageable);
}
