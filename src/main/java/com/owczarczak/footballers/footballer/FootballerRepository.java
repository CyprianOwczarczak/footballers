package com.owczarczak.footballers.footballer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

public interface FootballerRepository extends JpaRepository<Footballer, Integer> {

    // poczytać o JPQL --> przerobić na JPQL

//    @Query("SELECT a FROM Footballer a WHERE a.height")
    Page<Footballer> findAllByOrderByHeightDesc(Pageable pageable);

    @Query("SELECT f FROM Footballer f WHERE f.name = :name")
    List<Footballer> findByName(@Param("name") String name);

//    @Query("SELECT f FROM Footballer f WHERE f.name = :name")
    boolean existsByPesel(String pesel);

//    @Query("DELETE f FROM Footballer f WHERE f.id = :id")
    void deleteAllById(@Param("id") int id);
}
