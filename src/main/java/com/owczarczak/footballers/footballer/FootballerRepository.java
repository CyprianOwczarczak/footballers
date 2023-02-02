package com.owczarczak.footballers.footballer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FootballerRepository extends JpaRepository<Footballer, Integer> {

    //TODO Check if it works
    @Query("select * from footballers order by height desc limit 3")
    List<Footballer> get3HighestFootballers();

    @Query("select * form footballers where 'name' like '%search%'")
    List<Footballer> getFootballersByName(@Param("search") String name);

    List<Footballer> findByName(@Param("name") String name);
}
