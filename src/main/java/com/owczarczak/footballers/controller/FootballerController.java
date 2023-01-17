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

    //TODO Rozwinąć, pomiędzy Coptroller a Repository wstawić Service (do wszystkich metod)
    @GetMapping("/")
    public List<Footballer> getFootballers() {
        List<Footballer> footballers = repository.findAll();
        return footballers;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Footballer>> getFootballer(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repository.findById(id));
    }

    //TODO na SQL
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
    }

    //TODO Validacja sprawdzająca poprawność zaoytania (Ręcznie zrobić)
    @PostMapping
    ResponseEntity<Footballer> addFootballer(@RequestBody Footballer footballerToAdd) {
        Footballer result = repository.save(footballerToAdd);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    //TODO Validacja
    @PutMapping("/{id}")
    ResponseEntity<?> updateFootballer(@PathVariable int id, @RequestBody Footballer footballerToUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        footballerToUpdate.setId(id);
        Footballer result = repository.save(footballerToUpdate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Footballer> deleteFootballer(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }
}
