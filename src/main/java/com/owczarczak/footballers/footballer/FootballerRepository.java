package com.owczarczak.footballers.footballer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FootballerRepository extends JpaRepository<Footballer, Integer> {

    //fixme maybe limit doesn't work
    @Query("from Footballer s order by s.height desc")
    List<Footballer> get3HighestFootballers();

    //check
    @Query("from Footballer s where s.name like '%search%'")
    List<Footballer> getFootballersByName(@Param("search") String name);

    List<Footballer> findByName(String name);
}
