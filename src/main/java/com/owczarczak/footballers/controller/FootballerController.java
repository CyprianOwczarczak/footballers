package com.owczarczak.footballers.controller;

import com.owczarczak.footballers.database.FootballerRepository;
import com.owczarczak.footballers.model.Footballer;
import com.owczarczak.footballers.model.FootballerHeightComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @GetMapping("/top3ByHeight")
    public List<Footballer> getHighest() {
        List<Footballer> footballers = repository.findAll();
        footballers.sort(new FootballerHeightComparator());
        if (footballers.size() == 0) {
            return null;
        }
        if (footballers.size() < 3) {
            return footballers.subList(0, footballers.size());
        }
        return footballers.subList(0, 3);
        //TODO Add a @Query to Repository and use ResponseEntity
    }

    @PostMapping
    ResponseEntity<Footballer> addFootballer(@RequestBody Footballer footballerToAdd) {
        Footballer result = repository.save(footballerToAdd);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateFootballer(@PathVariable int id, @RequestBody Footballer footballerToUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(footballer -> footballer.updateFootballer(footballerToUpdate));
        repository.save()
        return ResponseEntity.noContent().build();
    }

    //    @PostMapping
//    Footballer addFootballer(@RequestBody Footballer footballerToAdd) {
//        Footballer result = repository.save(footballerToAdd);
//        return result;
//    }
//

    @DeleteMapping("/{id}")
    ResponseEntity<Footballer> deleteFootballer(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
