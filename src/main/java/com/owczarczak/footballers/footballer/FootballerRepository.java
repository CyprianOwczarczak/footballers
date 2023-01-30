package com.owczarczak.footballers.footballer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FootballerRepository extends JpaRepository<Footballer, Integer> {
    @Query(value = """
            select *
            from footballers
            order by height desc
            limit 3
            """, nativeQuery = true)
    List<Footballer> get3HighestFootballers();

    @Query(value = """
            select *
            from footballers
            where 'name' like '%search%'
            """, nativeQuery = true)
        //FIXME JPQL zamiast nativeQuery !
    List<Footballer> getFootballersByName(@Param("search") String name);

    List<Footballer> findByName(@Param("name") String name);
}
