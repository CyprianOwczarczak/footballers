package com.owczarczak.footballers.controller;

import com.owczarczak.footballers.database.FootballerRepository;
import com.owczarczak.footballers.model.Footballer;
import com.owczarczak.footballers.model.FootballerHeightComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/footballers")
public class FootballerController {
    @Autowired
    FootballerRepository repository;

    @GetMapping
    public String index(Footballer footballer) {
        return "Greetings !";
    }

    @GetMapping("/")
    public List<Footballer> getFootballers() {
        List<Footballer> footballers = repository.findAll();
        footballers.forEach(System.out::println);
        return footballers;
    }

    @GetMapping("/{id}")
    public Optional<Footballer> getFootballers(@PathVariable int id) {
        return repository.findById(id);
    }

    @GetMapping("top3ByHeight")
    public List<Footballer> getHighest() {
        List<Footballer> footballers = repository.findAll();
        footballers.sort(new FootballerHeightComparator());
        //TODO add a check if the List has 3 or less footbalers
        return footballers.subList(0, 3);
    }
    //TODO add more mappings
}
