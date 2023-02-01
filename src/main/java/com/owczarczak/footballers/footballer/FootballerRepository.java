package com.owczarczak.footballers.footballer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FootballerRepository extends JpaRepository<Footballer, Integer> {

    @Query("select * from footballers order by height desc limit 3")
    List<FootballerDto> get3HighestFootballers();

    @Query("select * form footballers where 'name' like '%search%'")
    List<FootballerDto> getFootballersByName(@Param("search") String name);

    List<FootballerDto> findByName(@Param("name") String name);
}
