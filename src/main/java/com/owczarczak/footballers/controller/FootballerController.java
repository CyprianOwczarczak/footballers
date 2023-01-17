package com.owczarczak.footballers.controller;

import com.owczarczak.footballers.database.FootballerRepository;
import com.owczarczak.footballers.model.Footballer;
import com.owczarczak.footballers.model.FootballerHeightComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
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

    //TODO Rozwinąć, pomiędzy Copntroller a Repository wstawić Service (do wszystkich metod)

    @GetMapping("/")
    public List<Footballer> getFootballers() {
        List<Footballer> footballers = repository.findAll();
        return footballers;
    }

    @GetMapping("/{id}")
    public Optional<Footballer> getFootballers(@PathVariable int id) {
        //TODO dodać 404a i zwracać ResponseEntity
        return repository.findById(id);
    }

    @GetMapping("/top3ByHeight")
    //TODO na SQL
    //
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
    }

    @PostMapping
    ResponseEntity<Footballer> addFootballer(@RequestBody Footballer footballerToAdd) {
        //TODO Validacja sprawdzająca poprawność zaoytania (Ręcznie zrobić)
        Footballer result = repository.save(footballerToAdd);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateFootballer(@PathVariable int id, @RequestBody Footballer footballerToUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        footballerToUpdate.setId(id);
        repository.save(footballerToUpdate);
        //TODO zwrcacać created (tak jak w postMapping)
        //Validacja
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Footballer> deleteFootballer(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            //TODO Sprawdzić czy nie lepiej zwracać 204 (no content)
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }
}
