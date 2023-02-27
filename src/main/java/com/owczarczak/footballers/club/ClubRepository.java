package com.owczarczak.footballers.club;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Integer> {
//    @Query("SELECT c FROM club c ORDER BY c.name")
//    Page<Club> findAllOrderByName(Pageable pageable);

    @Query(value = """
            select *
            from club where 
            """, nativeQuery = true)
    List<Club> findAllClubsSortByName();
}
