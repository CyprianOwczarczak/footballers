package com.owczarczak.footballers.footballer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface FootballerRepository extends JpaRepository<Footballer, Integer> {

    //Derived queries --> read
    Page<Footballer> findAllByOrderByHeightDesc(Pageable pageable);

    List<Footballer> findByName(String name);

    boolean existsByPesel(String pesel);

    void deleteAllById(int id);
}
