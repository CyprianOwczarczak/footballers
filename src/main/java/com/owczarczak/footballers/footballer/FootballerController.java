package com.owczarczak.footballers.footballer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/footballers")
public class FootballerController {

    //TODO change to service
    @Autowired
    FootballerRepository repository;

    @GetMapping
    public String index(Footballer footballer) {
        return "Greetings !";
    }

    @GetMapping("/")
    public List<Footballer> getAllFootballers() {
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

    @GetMapping("/top3ByHeight")
    public List<Footballer> getHighest() {
        return repository.get3HighestFootballers();
    }

//    @GetMapping("/{name}")
//    public List<Footballer> getFootballersByName(@RequestParam String name) {
//        return repository.getFootballersByName(name);
//    }

    @PostMapping
    ResponseEntity<Footballer> addFootballer(@RequestBody @Valid Footballer footballerToAdd) {
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
